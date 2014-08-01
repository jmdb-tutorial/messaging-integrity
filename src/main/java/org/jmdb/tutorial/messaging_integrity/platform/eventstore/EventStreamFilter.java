package org.jmdb.tutorial.messaging_integrity.platform.eventstore;

public interface EventStreamFilter {

    boolean accept(Event event);
}