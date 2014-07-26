package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStream implements EventStream {

    private List<Event> events = new ArrayList<>();

    @Override public <T> Event storeEvent(String userId, String eventType, T data) {
        Event event = new DataEvent<>(userId, eventType, data);
        events.add(event);
        return event;
    }

    @Override public Event updateStatusOfEvent(String eventId, EventStatus newStatus) {
        int indexOfEvent = indexOfEvent(eventId);
        Event event = events.get(indexOfEvent);

        Event newEvent = event.changeStatusTo(newStatus);

        events.remove(indexOfEvent);
        events.add(indexOfEvent, newEvent);

        return event;
    }

    @Override public Event getLastEvent() {
        if (events.size() == 0) {
            throw new IllegalStateException("No events here!");
        }
        return events.get(events.size() - 1);
    }

    @Override public List<Event> filterEvents(EventStreamFilter eventStreamFilter) {
        List<Event> matchingEvents = new ArrayList<>();

        for (Event event : events) {
            if (eventStreamFilter.accept(event)) {
                matchingEvents.add(event);
            }

        }
        return matchingEvents;
    }

    private int indexOfEvent(String eventId) {
        int i = 0;
        for (Event event : events) {
            if (eventId.equals(event.getId())) {
                return i;
            }
            ++i;
        }
        throw new RuntimeException(String.format("Event with id [%s] not found!", eventId));
    }
}