package org.jmdb.tutorial.messaging_resilience;

public class ApplicationRequestProcessor {

    private final ApplicationRepository applicationRepository;

    public ApplicationRequestProcessor(ApplicationRepository applicationRepository, ApplicationHistoryRepository applicationHistoryRepository) {
        this.applicationRepository = applicationRepository;
    }


    public void processRequest(CreateApplicationRequest request) {
        applicationRepository.put(request.toApplication());

    }
}