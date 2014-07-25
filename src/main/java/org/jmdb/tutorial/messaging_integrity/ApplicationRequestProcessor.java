package org.jmdb.tutorial.messaging_integrity;

public class ApplicationRequestProcessor {

    private final ApplicationRepository applicationRepository;
    private ApplicationHistoryPublisher applicationHistoryPublisher;
    private ApplicationHistoryRepository applicationHistoryRepository;

    public ApplicationRequestProcessor(ApplicationRepository applicationRepository,
                                       ApplicationHistoryPublisher applicationHistoryPublisher) {
        this.applicationRepository = applicationRepository;
        this.applicationHistoryPublisher = applicationHistoryPublisher;
    }


    public void processRequest(CreateApplicationRequest request) {
        Application application = request.toApplication();

        this.applicationRepository.put(application);

        this.applicationHistoryPublisher.publishApplicationCreated(application);

    }
}