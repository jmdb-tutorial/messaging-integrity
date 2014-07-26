package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.Date;

import static java.util.UUID.*;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class DataEvent<T> implements Event {

    private final String eventId;
    private final String userId;
    private final T data;
    private final String timeStamp;
    private final EventStatus eventStatus;
    private String eventType;

    public DataEvent(String userId, String eventType, T data) {
        this.eventType = eventType;
        this.eventId = randomUUID().toString();
        this.userId = userId;
        this.data = data;
        this.eventStatus = RECORDED;
        this.timeStamp = new Date().toString();
    }

    private DataEvent(String eventId, String userId,
                      T data, String timeStamp, EventStatus newEventStatus) {
        this.eventId = eventId;
        this.userId = userId;
        this.data = data;
        this.timeStamp = timeStamp;
        this.eventStatus = newEventStatus;
    }


    @Override public String getUserId() {
        return userId;
    }

    @Override public String getTimeStamp() {
        return timeStamp;
    }

    @Override public EventStatus getEventStatus() {
        return eventStatus;
    }

    @Override public Event changeStatusTo(EventStatus newEventStatus) {
        return new DataEvent(eventId, userId, data, timeStamp, newEventStatus);
    }

    @Override public String getId() {
        return eventId;
    }

    public T getData() {
        return this.data;
    }
}