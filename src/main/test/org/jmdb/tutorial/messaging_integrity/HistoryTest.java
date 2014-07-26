package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationHistoryRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequest;
import org.jmdb.tutorial.messaging_integrity.applications.InMemoryApplicationAdminRepository;
import org.jmdb.tutorial.messaging_integrity.applications.InMemoryApplicationRepository;
import org.jmdb.tutorial.messaging_integrity.history.ApplicationHistoryPublisher;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_integrity.applications.CreateApplicationRequestBuilder.createApplicationRequest;

public class HistoryTest {

    private ApplicationRequestProcessor applicationRequestProcessor;
    private ApplicationHistoryRepository applicationHistoryRepository;
    private CreateApplicationRequest createApplicationRequest;
    private ApplicationRepository applicationRepository;
    private InMemoryApplicationAdminRepository applicationAdminRepository;

    @Before
    public void setUp() {
        applicationRepository = new InMemoryApplicationRepository();
        applicationAdminRepository = new InMemoryApplicationAdminRepository();
        applicationHistoryRepository = new InMemoryHistoryRepository();
        ApplicationHistoryPublisher publisher = new ApplicationHistoryPublisher(applicationHistoryRepository);

        applicationRequestProcessor = new ApplicationRequestProcessor(applicationRepository, publisher);
    }

    @Test
    public void happy_path() {
        createApplicationRequest = createApplicationRequest()
                .withId("APP-001")
                .build();


        applicationRequestProcessor.processRequest(createApplicationRequest);

        Application application = applicationRepository.getById("APP-001");
        History history = applicationHistoryRepository.getByApplicationId("APP-001");

        assertThat(application.getId(), equalTo("APP-001"));
        assertThat(applicationAdminRepository.getUnpublishedEvents().size(), equalTo(0));
        assertThat(history.getEvents().size(), equalTo(1));

    }
}