package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.List;

public interface EventStream {
    public <T> Event storeEvent(String userId, String eventType, T data);

    public Event updateStatusOfEvent(String eventId, EventStatus newStatus);

    Event getLastEvent();

    List<Event> filterEvents(EventStreamFilter eventStreamFilter);

    List<Event> getEventsWithStatus(EventStatus status);
}