package org.jmdb.tutorial.messaging_integrity.history;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.applications.ApplicationHistoryRepository;

public class ApplicationHistoryPublisher {
    private ApplicationHistoryRepository applicationHistoryRepository;

    public ApplicationHistoryPublisher(ApplicationHistoryRepository applicationHistoryRepository) {
        this.applicationHistoryRepository = applicationHistoryRepository;
    }

    public void publishApplicationCreated(Application application) {
        History history = new History(application.getId());
        history.addEvent(new History.CreatedEvent(application.getId()));
        applicationHistoryRepository.put(history);

    }
}