package org.jmdb.tutorial.messaging_integrity.email;

import java.util.Map;

import static java.util.UUID.randomUUID;

public class EmailRequestProcessor {

    private final EmailRepository emailRepository;
    private SMTPGateway smtpGateway;
    private final EmailEventPublisher messaging;


    public EmailRequestProcessor(EmailRepository emailRepository,
                                 SMTPGateway smtpGateway,
                                 EmailEventPublisher messaging) {
        this.emailRepository = emailRepository;
        this.smtpGateway = smtpGateway;
        this.messaging = messaging;
    }

    public String sendEmail(String emailAddress, String templateName, Map<String, String> data) {
        Email email = new Email(randomUUID().toString(), emailAddress, templateName, data);

        emailRepository.put(email);

        smtpGateway.sendEmail(email);

        messaging.publishEmailSentEvent(email);

        return email.getId();
    }
}