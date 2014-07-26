package org.jmdb.tutorial.messaging_integrity.eventstore;

import org.jmdb.tutorial.messaging_integrity.email.Email;

import java.util.List;

public interface EventStore {
    EventStream eventStreamFor(String id);

    Event getEventById(String eventId);

    List<Event> getAllEventsWithStatus(EventStatus eventStatus);
}