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

    @Before
    public void setUp() {
        ApplicationRepository applicationRepository = new InMemoryApplicationRepository();
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

        ApplicationHistory history = applicationHistoryRepository.getByApplicationId("APP-001");

        assertThat(history.hasEvents(), equalTo(true));

    }
}