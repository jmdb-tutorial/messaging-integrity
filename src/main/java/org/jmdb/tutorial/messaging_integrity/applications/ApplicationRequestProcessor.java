package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.platform.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static org.jmdb.tutorial.messaging_integrity.platform.eventstore.StandardEventStatus.PUBLISHED;

public class ApplicationRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRequestProcessor.class);
    private AuthorisationContext auth;
    private EventStore eventStore;
    private ApplicationEventQueue applicationEventQueue;


    public ApplicationRequestProcessor(AuthorisationContext auth, EventStore eventStore, ApplicationEventQueue applicationEventQueue) {
        this.auth = auth;
        this.eventStore = eventStore;
        this.applicationEventQueue = applicationEventQueue;
    }


    public void processRequest(CreateApplicationRequest request) {
        Application application = request.toApplication();

        EventStream eventStream = eventStore.eventStreamFor(application.id);
        Event event = eventStream.storeEvent(auth.currentUserId(), "application-created", application);

        try {
            applicationEventQueue.publishApplicationCreated(application);

            eventStream.updateStatusOfEvent(event.eventId, PUBLISHED);

        } catch (FailedToPublishException e) {
            log.error(format("Failed to publish [application-created] for application id [%s]", application.id), e);
        }

    }
}