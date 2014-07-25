package org.jmdb.tutorial.messaging_integrity;

import java.util.ArrayList;
import java.util.List;

public class ApplicationHistory {

    private final String applicationId;
    private final List<HistoryEvent> events = new ArrayList<>();

    public ApplicationHistory(String applicationId) {
        this.applicationId = applicationId;
    }

    public boolean hasEvents() {
        return events.isEmpty();
    }

    public void addEvent(CreatedEvent createdEvent) {


    }

    public String getApplicationId() {
        return applicationId;
    }

    public static class CreatedEvent {
        public CreatedEvent(String id) {

        }
    }
}