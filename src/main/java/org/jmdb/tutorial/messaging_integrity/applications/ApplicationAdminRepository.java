package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.platform.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStatus;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStream;

import java.util.List;

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