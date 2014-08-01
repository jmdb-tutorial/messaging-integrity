package org.jmdb.tutorial.messaging_integrity.email;


import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;

public class EmailEventQueue {
    private HistoryRepository historyRepository;

    public EmailEventQueue(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void publishEmailSent(Email email) {
        History history = new History(email.customerId);
        history.addEvent(new HistoryEvent("email-sent"));
        historyRepository.put(history);

    }
}