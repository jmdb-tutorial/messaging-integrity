package org.jmdb.tutorial.messaging_integrity;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_integrity.CreateApplicationRequestBuilder.createApplicationRequest;

public class ApplicationHistoryTest {

    private ApplicationRequestProcessor applicationRequestProcessor;
    private ApplicationHistoryRepository applicationHistoryRepository;
    private CreateApplicationRequest createApplicationRequest;
    private ApplicationRepository applicationRepository;

    @Before
    public void setUp() {
        applicationRepository = new InMemoryApplicationRepository();
        applicationHistoryRepository = new InMemoryApplicationHistoryRepository();
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
        ApplicationHistory history = applicationHistoryRepository.getByApplicationId("APP-001");

        assertThat(application.getId(), equalTo("APP-001"));
        assertThat(history.getEvents().size(), equalTo(1));

    }
}