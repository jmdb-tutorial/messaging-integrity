package org.jmdb.tutorial.messaging_integrity.applications;


import org.jmdb.tutorial.messaging_integrity.history.ApplicationHistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class ApplicationEventQueue {
    private HistoryRepository historyRepository;

    public ApplicationEventQueue(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void publishApplicationCreated(Application application) {
        History history = new History(application.customerId);
        history.addEvent(new ApplicationHistoryEvent("application-created", application.id));
        historyRepository.put(history);

    }
}