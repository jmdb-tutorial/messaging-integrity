package org.jmdb.tutorial.messaging_integrity.history;

public class ApplicationHistoryEvent extends HistoryEvent {
    private String applicationId;

    public ApplicationHistoryEvent(String eventType, String applicationId) {
        super(eventType);
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }
}