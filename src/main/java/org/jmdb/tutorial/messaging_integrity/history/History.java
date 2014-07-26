package org.jmdb.tutorial.messaging_integrity.history;

import java.util.ArrayList;
import java.util.List;

public class History {

    private final String applicationId;
    private final List<HistoryEvent> events = new ArrayList<>();

    public History(String applicationId) {
        this.applicationId = applicationId;
    }

    public boolean hasEvents() {
        return events.isEmpty();
    }

    public void addEvent(CreatedEvent createdEvent) {
       events.add(createdEvent);
    }

    public String getApplicationId() {
        return applicationId;
    }

    public List<HistoryEvent> getEvents() {
        return events;
    }

    public static class CreatedEvent implements HistoryEvent {
        public CreatedEvent(String id) {

        }
    }
}