package org.jmdb.tutorial.messaging_integrity.eventstore;

import java.util.HashMap;
import java.util.Map;

public class InMemoryEventStore implements EventStore {

    private final Map<String, EventStream> eventStreamMap = new HashMap<>();

    @Override public EventStream eventStreamFor(String id) {
        if (eventStreamMap.containsKey(id)) {
            return eventStreamMap.get(id);
        }

        EventStream eventStream = new InMemoryEventStream();
        eventStreamMap.put(id, eventStream);

        return eventStream;
    }
}