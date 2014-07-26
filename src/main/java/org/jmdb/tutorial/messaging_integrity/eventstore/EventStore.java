package org.jmdb.tutorial.messaging_integrity.eventstore;

public interface EventStore {
    EventStream eventStreamFor(String id);
}