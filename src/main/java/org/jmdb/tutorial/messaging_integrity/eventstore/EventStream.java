package org.jmdb.tutorial.messaging_integrity.eventstore;

public interface EventStream {
    public <T> Event storeEvent(String userId, T data);

    public Event changeStatusOfEvent(String eventId, EventStatus newStatus);

    Event getLastEvent();
}