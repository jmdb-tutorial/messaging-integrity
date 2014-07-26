package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.applications.Application;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class ApplicationHistoryPublisher {
    private HistoryRepository historyRepository;

    public ApplicationHistoryPublisher(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void publishApplicationCreated(Application application) {
        History history = new History(application.getId());
        history.addEvent(new History.CreatedEvent(application.getId()));
        historyRepository.put(history);

    }
}