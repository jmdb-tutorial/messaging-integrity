package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.platform.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class ApplicationRepository {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRepository.class);

    private final EventStore eventStore;

    public ApplicationRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Application getById(String applicationId) {
        EventStream eventStream = eventStore.eventStreamFor(applicationId);
        return ((DataEvent<Application>)eventStream.getLastEvent()).getData();
    }

}