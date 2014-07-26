package org.jmdb.tutorial.messaging_integrity.history;

import java.util.ArrayList;
import java.util.List;

public class History {

    private final String customerId;
    private final List<HistoryEvent> events = new ArrayList<>();

    public History(String customerId) {
        this.customerId = customerId;
    }

    public boolean hasEvents() {
        return events.isEmpty();
    }

    public void addEvent(HistoryEvent event) {
       events.add(event);
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<HistoryEvent> getEvents() {
        return events;
    }

}