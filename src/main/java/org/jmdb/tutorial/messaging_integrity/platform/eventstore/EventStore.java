package org.jmdb.tutorial.messaging_integrity.platform.eventstore;

import java.util.List;

public interface EventStore {
    EventStream eventStreamFor(String id);

    Event getEventById(String eventId);

    List<Event> getAllEventsWithStatus(EventStatus eventStatus);
}