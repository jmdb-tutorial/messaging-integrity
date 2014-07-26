package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.List;

public interface EventStream {
    public <T> Event storeEvent(String userId, T data);

    public Event changeStatusOfEvent(String eventId, EventStatus newStatus);

    Event getLastEvent();

    List<Event> filterEvents(EventStreamFilter eventStreamFilter);
}