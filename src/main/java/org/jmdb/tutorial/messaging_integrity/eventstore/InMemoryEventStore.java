package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEventStore implements EventStore {

    private final Map<String, EventStream> eventStreamMap = new HashMap<>();
    private final Map<String, Event> eventIndex = new HashMap<>();

    @Override public EventStream eventStreamFor(String id) {
        if (eventStreamMap.containsKey(id)) {
            return eventStreamMap.get(id);
        }

        EventStream eventStream = new InMemoryEventStream(this);
        eventStreamMap.put(id, eventStream);

        return eventStream;
    }

    @Override public Event getEventById(String eventId) {
        return eventIndex.get(eventId);
    }

    @Override public List<Event> getAllEventsWithStatus(EventStatus eventStatus) {
        List<Event> matchingEvents = new ArrayList<>();

        for (EventStream stream : eventStreamMap.values()) {
            matchingEvents.addAll(stream.getEventsWithStatus(eventStatus));
        }
        return matchingEvents;
    }

    void registerEvent(Event event) {
         eventIndex.put(event.getId(), event);
    }
}