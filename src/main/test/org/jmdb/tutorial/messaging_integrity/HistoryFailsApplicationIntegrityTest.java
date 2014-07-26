package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationAdminRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationHistoryPublisher;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequest;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequestBuilder.createApplicationRequest;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class HistoryFailsApplicationIntegrityTest {

    private HistoryRepository historyRepository;
    private ApplicationRepository applicationRepository;
    private ApplicationAdminRepository applicationAdminRepository;

    @Before
    public void setUp() {
        EventStore eventStore = new InMemoryEventStore();

        historyRepository = new InMemoryHistoryRepository();
        ApplicationHistoryPublisher publisher = new FailingHistoryPublisher(historyRepository, "APP-001");

        applicationRepository = new ApplicationRepository(eventStore, publisher, new AuthorisationContext());
        applicationAdminRepository = new ApplicationAdminRepository(eventStore);

        ApplicationRequestProcessor applicationRequestProcessor = new ApplicationRequestProcessor(applicationRepository);

        CreateApplicationRequest createApplicationRequest = createApplicationRequest()
                .withId("APP-001")
                .build();

        try {
            applicationRequestProcessor.processRequest(createApplicationRequest);
        } catch (FailedToPublishException e) {
            // we expect this to happen, and want to examine our state afterwards.
        }

    }

    @Test
    public void application_is_stored() {
        Application application = applicationRepository.getById("APP-001");

        assertThat(application.getId(), equalTo("APP-001"));
    }

    @Test
    public void events_do_not_appear_in_the_history() {
        History history = historyRepository.getByApplicationId("APP-001");

        assertThat(history, nullValue());
    }

    @Test
    public void all_events_are_marked_as_only_recorded() {
        assertThat(applicationAdminRepository.getApplicationEventsByStatus("APP-001", RECORDED).size(), equalTo(1));
    }


    private static class FailingHistoryPublisher extends ApplicationHistoryPublisher {

        private final String idToFailOn;

        public FailingHistoryPublisher(HistoryRepository historyRepository, String idToFailOn) {
            super(historyRepository);
            this.idToFailOn = idToFailOn;
        }

        @Override public void publishApplicationCreated(Application application) {
            if (idToFailOn.equals(application.getId())) {
                throw new FailedToPublishException();
            }
            super.publishApplicationCreated(application);
        }


    }

    private static class FailedToPublishException extends RuntimeException {
        private FailedToPublishException() {
            super("BANG! we failed to reach the messagebroker!");
        }
    }
}