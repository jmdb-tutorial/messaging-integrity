package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus;

import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class EmailRequestProcessor {


    private final AuthorisationContext auth;
    private final EventStore eventStore;
    private final SMTPGateway smtpGateway;
    private final EmailEventPublisher messaging;


    public EmailRequestProcessor(AuthorisationContext auth,
                                 EventStore eventStore,
                                 SMTPGateway smtpGateway,
                                 EmailEventPublisher messaging) {
        this.auth = auth;
        this.eventStore = eventStore;
        this.smtpGateway = smtpGateway;
        this.messaging = messaging;
    }

    public String sendEmail(String customerId, String emailAddress, String templateName, Map<String, String> data) {
        Email email = new Email(customerId, emailAddress, templateName, data);

        EventStream eventStream = eventStore.eventStreamFor(email.getAddress());
        Event event = eventStream.storeEvent(auth.getCurrentUserId(), "send-email", email);


        smtpGateway.sendEmail(email);


        messaging.publishEmailSentEvent(email);

        eventStream.updateStatusOfEvent(event.getId(), PUBLISHED);

        return event.getId();
    }
}