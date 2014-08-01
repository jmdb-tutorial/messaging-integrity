package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class ApplicationRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRequestProcessor.class);
    private AuthorisationContext auth;
    private EventStore eventStore;
    private ApplicationEventPublisher messaging;


    public ApplicationRequestProcessor(AuthorisationContext auth, EventStore eventStore, ApplicationEventPublisher messaging) {
        this.auth = auth;
        this.eventStore = eventStore;
        this.messaging = messaging;
    }


    public void processRequest(CreateApplicationRequest request) {
        Application application = request.toApplication();

        EventStream eventStream = eventStore.eventStreamFor(application.id);
        Event event = eventStream.storeEvent(auth.getCurrentUserId(), "application-created", application);

        try {
            messaging.publishCreatedEvent(application);

            eventStream.updateStatusOfEvent(event.getId(), PUBLISHED);

        } catch (FailedToPublishException e) {
            log.error(format("Failed to publish [application-created] for application id [%s]", application.id), e);
        }

    }
}