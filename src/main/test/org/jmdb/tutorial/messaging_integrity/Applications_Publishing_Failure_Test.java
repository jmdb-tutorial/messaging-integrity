package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationAdminRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationEventQueue;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequest;
import org.jmdb.tutorial.messaging_integrity.applications.FailedToPublishException;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequestBuilder.createApplicationRequest;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class Applications_Publishing_Failure_Test {

    private HistoryRepository historyRepository;
    private ApplicationRepository applicationRepository;
    private ApplicationAdminRepository applicationAdminRepository;

    @Before
    public void setUp() {
        AuthorisationContext auth = new AuthorisationContext();
        EventStore eventStore = new InMemoryEventStore();

        historyRepository = new InMemoryHistoryRepository();
        ApplicationEventQueue publisher = new FailingEventQueue(historyRepository, "APP-001");

        applicationRepository = new ApplicationRepository(eventStore);
        applicationAdminRepository = new ApplicationAdminRepository(eventStore);

        ApplicationRequestProcessor applicationRequestProcessor = new ApplicationRequestProcessor(auth, eventStore, publisher);

        CreateApplicationRequest createApplicationRequest = createApplicationRequest()
                .withId("APP-001")
                .build();


        applicationRequestProcessor.processRequest(createApplicationRequest);
    }

    @Test
    public void application_is_stored() {
        Application application = applicationRepository.getById("APP-001");

        assertThat(application.id, equalTo("APP-001"));
    }

    @Test
    public void events_do_not_appear_in_the_history() {
        History history = historyRepository.getByCustomerId("APP-001");

        assertThat(history, nullValue());
    }

    @Test
    public void all_events_are_marked_as_only_recorded() {
        assertThat(applicationAdminRepository.getApplicationEventsByStatus("APP-001", RECORDED).size(), equalTo(1));
    }


    private static class FailingEventQueue extends ApplicationEventQueue {

        private final String idToFailOn;

        private FailingEventQueue(HistoryRepository historyRepository, String idToFailOn) {
            super(historyRepository);
            this.idToFailOn = idToFailOn;
        }

        @Override public void publishApplicationCreated(Application application) {
            if (idToFailOn.equals(application.id)) {
                throw new FailedToPublishException();
            }
            super.publishApplicationCreated(application);
        }


    }

}