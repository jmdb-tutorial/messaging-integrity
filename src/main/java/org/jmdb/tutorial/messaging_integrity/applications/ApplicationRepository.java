package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class ApplicationRepository {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRepository.class);

    private final EventStore eventStore;
    private final ApplicationHistoryPublisher applicationHistoryPublisher;
    private AuthorisationContext auth;

    public ApplicationRepository(EventStore eventStore,
                                 ApplicationHistoryPublisher applicationHistoryPublisher,
                                 AuthorisationContext auth) {
        this.eventStore = eventStore;
        this.applicationHistoryPublisher = applicationHistoryPublisher;
        this.auth = auth;
    }

    public Application getById(String applicationId) {
        EventStream eventStream = eventStore.eventStreamFor(applicationId);
        return ((DataEvent<Application>)eventStream.getLastEvent()).getData();
    }

    public void create(Application application) {
        EventStream eventStream = eventStore.eventStreamFor(application.getId());
        Event event = eventStream.storeEvent(auth.getCurrentUserId(), "application-created", application);

        try {
            applicationHistoryPublisher.publishApplicationCreated(application);

            eventStream.updateStatusOfEvent(event.getId(), PUBLISHED);

        } catch (FailedToPublishException e) {
            log.error(format("Failed to publish [application-created] for application id [%s]", application.getId()), e);
        }
    }

}