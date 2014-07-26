package org.jmdb.tutorial.messaging_integrity.applications;


import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class ApplicationEventPublisher {
    private HistoryRepository historyRepository;

    public ApplicationEventPublisher(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void publishCreatedEvent(Application application) {
        History history = new History(application.getCustomerId());
        history.addEvent(new HistoryEvent("application-created"));
        historyRepository.put(history);

    }
}