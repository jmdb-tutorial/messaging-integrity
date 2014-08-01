package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.platform.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationRepository {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRepository.class);

    private final EventStore eventStore;

    public ApplicationRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Application getById(String applicationId) {
        EventStream eventStream = eventStore.eventStreamFor(applicationId);
        Event<Application> lastEvent = eventStream.getLastEvent();
        return lastEvent.payload;
    }

}