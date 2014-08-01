package org.jmdb.tutorial.messaging_integrity.platform.eventstore;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStream implements EventStream {

    private final List<Event> events = new ArrayList<>();
    private final InMemoryEventStore parentStore;

    public InMemoryEventStream(InMemoryEventStore parentStore) {
        this.parentStore = parentStore;
    }

    @Override public <T> Event storeEvent(String userId, String eventType, T data) {
        Event<T> event = new Event<>(userId, eventType, data);
        events.add(event);

        parentStore.registerEvent(event);

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

    @Override public List<Event> getEventsWithStatus(final EventStatus status) {
        return filterEvents(new EventStreamFilter() {
            @Override public boolean accept(Event event) {
                return status.equals(event.eventStatus);
            }
        });
    }

    private int indexOfEvent(String eventId) {
        int i = 0;
        for (Event event : events) {
            if (eventId.equals(event.eventId)) {
                return i;
            }
            ++i;
        }
        throw new RuntimeException(String.format("Event with id [%s] not found!", eventId));
    }
}