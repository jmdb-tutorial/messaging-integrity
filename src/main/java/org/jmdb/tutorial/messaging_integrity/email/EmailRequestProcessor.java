package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.applications.FailedToPublishException;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static java.lang.String.format;
import static org.jmdb.tutorial.messaging_integrity.email.EmailStatus.SENT;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.PUBLISHED;

public class EmailRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(EmailRequestProcessor.class);

    private final AuthorisationContext auth;
    private final EventStore eventStore;
    private final SMTPGateway smtpGateway;
    private final EmailEventQueue emailEventQueue;


    public EmailRequestProcessor(AuthorisationContext auth,
                                 EventStore eventStore,
                                 SMTPGateway smtpGateway,
                                 EmailEventQueue emailEventQueue) {
        this.auth = auth;
        this.eventStore = eventStore;
        this.smtpGateway = smtpGateway;
        this.emailEventQueue = emailEventQueue;
    }

    public String sendEmail(String customerId, String emailAddress, String templateName, Map<String, String> data) {
        Email email = new Email(customerId, emailAddress, templateName, data);

        EventStream eventStream = eventStore.eventStreamFor(email.address);
        Event event = eventStream.storeEvent(auth.currentUserId(), "send-email", email);


        try {
            smtpGateway.sendEmail(email);

            eventStream.updateStatusOfEvent(event.getId(), SENT);

            emailEventQueue.publishEmailSent(email);

            eventStream.updateStatusOfEvent(event.getId(), PUBLISHED);

        } catch (FailedToSendEmailException e) {
            log.error(format("SMTP Gateway failed whilst sending email to [%s]", emailAddress), e);
        } catch (FailedToPublishException e)  {
            log.error(format("Failed to publish email event sending to [%s]", emailAddress), e);
        }

        return event.getId();

    }
}