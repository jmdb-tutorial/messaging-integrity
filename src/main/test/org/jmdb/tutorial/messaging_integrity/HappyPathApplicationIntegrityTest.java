package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationAdminRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationEventPublisher;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequest;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.history.ApplicationHistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequestBuilder.createApplicationRequest;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class HappyPathApplicationIntegrityTest {

    private HistoryRepository historyRepository;
    private ApplicationRepository applicationRepository;
    private ApplicationAdminRepository applicationAdminRepository;

    @Before
    public void setUp() {
        EventStore eventStore = new InMemoryEventStore();
        AuthorisationContext auth = new AuthorisationContext();

        historyRepository = new InMemoryHistoryRepository();
        ApplicationEventPublisher publisher = new ApplicationEventPublisher(historyRepository);

        applicationRepository = new ApplicationRepository(eventStore);
        applicationAdminRepository = new ApplicationAdminRepository(eventStore);

        ApplicationRequestProcessor applicationRequestProcessor = new ApplicationRequestProcessor(auth, eventStore, publisher);

        CreateApplicationRequest createApplicationRequest = createApplicationRequest()
                .withId("APP-001")
                .withCustomerId("CUST-001")
                .build();


        applicationRequestProcessor.processRequest(createApplicationRequest);

    }

    @Test
    public void application_is_stored() {
        Application application = applicationRepository.getById("APP-001");

        assertThat(application.getId(), equalTo("APP-001"));
    }

    @Test
    public void events_appear_in_the_history() {
        History history = historyRepository.getByCustomerId("CUST-001");

        List<ApplicationHistoryEvent> applicationEvents = history.getApplicationEvents();

        assertThat(applicationEvents.size(), equalTo(1));

        HistoryEvent historyEvent = history.getEvents().get(0);
        assertThat(historyEvent.getEventType(), equalTo("application-created") );
    }

    @Test
    public void all_events_are_marked_as_published() {
        assertThat(applicationAdminRepository.getApplicationEventsByStatus("APP-001", PUBLISHED).size(), equalTo(1));
    }


}