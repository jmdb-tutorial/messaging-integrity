package org.jmdb.tutorial.messaging_integrity.history;

public class HistoryEvent {

    private final String eventType;

    public HistoryEvent(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}