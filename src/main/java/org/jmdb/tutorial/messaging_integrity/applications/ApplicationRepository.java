package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus;

import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class ApplicationRepository {

    private final EventStore eventStore;
    private final ApplicationHistoryPublisher applicationHistoryPublisher;

    public ApplicationRepository(EventStore eventStore,
                                 ApplicationHistoryPublisher applicationHistoryPublisher) {
        this.eventStore = eventStore;
        this.applicationHistoryPublisher = applicationHistoryPublisher;
    }

    public Application getById(String applicationId) {
        EventStream eventStream = eventStore.eventStreamFor(applicationId);
        return ((DataEvent<Application>)eventStream.getLastEvent()).getData();
    }

    public void create(Application application) {
        EventStream eventStream = eventStore.eventStreamFor(application.getId());
        Event event = eventStream.storeEvent("system-user", "application-created", application);

        this.applicationHistoryPublisher.publishApplicationCreated(application);

        eventStream.changeStatusOfEvent(event.getId(), PUBLISHED);
    }

}