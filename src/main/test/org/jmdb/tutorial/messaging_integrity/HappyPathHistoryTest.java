package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationAdminRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationHistoryPublisher;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequest;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequestBuilder.createApplicationRequest;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class HappyPathHistoryTest {

    private HistoryRepository historyRepository;
    private ApplicationRepository applicationRepository;
    private ApplicationAdminRepository applicationAdminRepository;

    @Before
    public void setUp() {
        EventStore eventStore = new InMemoryEventStore();
        applicationRepository = new ApplicationRepository(eventStore);
        applicationAdminRepository = new ApplicationAdminRepository(eventStore);

        historyRepository = new InMemoryHistoryRepository();
        ApplicationHistoryPublisher publisher = new ApplicationHistoryPublisher(historyRepository);

        ApplicationRequestProcessor applicationRequestProcessor = new ApplicationRequestProcessor(applicationRepository, publisher);

        CreateApplicationRequest createApplicationRequest = createApplicationRequest()
                .withId("APP-001")
                .build();


        applicationRequestProcessor.processRequest(createApplicationRequest);

    }

    @Test
    public void application_is_stored() {
        Application application = applicationRepository.getById("APP-001");

        assertThat(application.getId(), equalTo("APP-001"));
    }

    @Test
    public void all_history_events_are_published() {
        History history = historyRepository.getByApplicationId("APP-001");

        assertThat(history.getEvents().size(), equalTo(1));
    }

    @Test
    public void all_events_are_published() {
        assertThat(applicationAdminRepository.filterEventsByStatus("APP-001", PUBLISHED).size(), equalTo(1));
    }
}