package org.jmdb.tutorial.messaging_integrity.eventstore;

public interface EventStreamFilter {

    boolean accept(Event event);
}