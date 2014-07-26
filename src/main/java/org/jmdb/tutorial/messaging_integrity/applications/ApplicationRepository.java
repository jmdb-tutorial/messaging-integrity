package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;

public class ApplicationRepository {

    private final EventStore eventStore;

    public ApplicationRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Application getById(String applicationId) {
        EventStream eventStream = eventStore.eventStreamFor(applicationId);
        return ((DataEvent<Application>)eventStream.getLastEvent()).getData();
    }

    public void create(Application application) {
        EventStream eventStream = eventStore.eventStreamFor(application.getId());
        eventStream.storeEvent("system-user", application);
    }
}