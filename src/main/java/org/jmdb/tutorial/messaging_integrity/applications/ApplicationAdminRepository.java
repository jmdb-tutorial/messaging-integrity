package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStatus;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStreamFilter;
import org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus;

import java.util.List;

import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class ApplicationAdminRepository {

    private final EventStore eventStore;

    public ApplicationAdminRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public List<Event> getApplicationEventsByStatus(String applicationId, EventStatus status) {
        EventStream eventStream = this.eventStore.eventStreamFor(applicationId);
        return eventStream.getEventsWithStatus(status);
    }
}