package org.jmdb.tutorial.messaging_resilience;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_resilience.CreateApplicationRequestBuilder.createApplicationRequest;

public class ApplicationHistoryTest {

    private ApplicationRequestProcessor applicationRequestProcessor;
    private ApplicationRepository applicationRepository;
    private ApplicationHistoryRepository applicationHistoryRepository;

    @Before
    public void setUp() {
        applicationRepository = new InMemoryApplicationRepository();
        applicationHistoryRepository = new InMemoryApplicationHistoryRepository();

        applicationRequestProcessor = new ApplicationRequestProcessor(applicationRepository, applicationHistoryRepository);
    }

    @Test
    public void happy_path() {
        CreateApplicationRequest createApplicationRequest = createApplicationRequest()
                                                                .withId("APP-001")
                                                                .build();


        applicationRequestProcessor.processRequest(createApplicationRequest);

        ApplicationHistory history = applicationHistoryRepository.getByApplicationId("APP-001");

        assertThat(history.hasEvents(), equalTo(true));

    }
}