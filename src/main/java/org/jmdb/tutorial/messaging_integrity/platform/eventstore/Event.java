package org.jmdb.tutorial.messaging_integrity.platform.eventstore;

import java.util.Date;

import static java.util.UUID.randomUUID;
import static org.jmdb.tutorial.messaging_integrity.platform.eventstore.StandardEventStatus.RECORDED;

public class Event<T> {

    public final String eventId;
    public final String userId;
    public final T payload;
    public final String timeStamp;
    public final EventStatus eventStatus;
    public final String eventType;

    public Event(String userId, String eventType, T payload) {
        this.eventType = eventType;
        this.eventId = randomUUID().toString();
        this.userId = userId;
        this.payload = payload;
        this.eventStatus = RECORDED;
        this.timeStamp = new Date().toString();
    }

    private Event(String eventId, String eventType, String userId,
                  T payload, String timeStamp, EventStatus newEventStatus) {
        this.eventType = eventType;
        this.eventId = eventId;
        this.userId = userId;
        this.payload = payload;
        this.timeStamp = timeStamp;
        this.eventStatus = newEventStatus;
    }


    public Event changeStatusTo(EventStatus newEventStatus) {
        return new Event(eventId, eventType, userId, payload, timeStamp, newEventStatus);
    }


}