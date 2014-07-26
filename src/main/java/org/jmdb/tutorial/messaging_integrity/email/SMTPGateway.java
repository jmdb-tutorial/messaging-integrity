package org.jmdb.tutorial.messaging_integrity.email;

import java.util.List;

public interface SMTPGateway {
    void sendEmail(Email email);

    List<Email> getSentMail();
}