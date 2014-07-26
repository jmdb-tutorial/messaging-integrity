package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class ApplicationRequestProcessor {

    private final ApplicationRepository applicationRepository;
    private ApplicationHistoryPublisher applicationHistoryPublisher;
    private HistoryRepository historyRepository;

    public ApplicationRequestProcessor(ApplicationRepository applicationRepository,
                                       ApplicationHistoryPublisher applicationHistoryPublisher) {
        this.applicationRepository = applicationRepository;
        this.applicationHistoryPublisher = applicationHistoryPublisher;
    }


    public void processRequest(CreateApplicationRequest request) {
        Application application = request.toApplication();

        this.applicationRepository.create(application);

        this.applicationHistoryPublisher.publishApplicationCreated(application);



    }
}