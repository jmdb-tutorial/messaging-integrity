package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class ApplicationRequestProcessor {

    private final ApplicationRepository applicationRepository;
    private HistoryRepository historyRepository;

    public ApplicationRequestProcessor(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public void processRequest(CreateApplicationRequest request) {
        Application application = request.toApplication();

        this.applicationRepository.create(application);

    }
}