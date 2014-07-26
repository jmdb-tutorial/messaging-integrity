package org.jmdb.tutorial.messaging_integrity.email;

import java.util.ArrayList;
import java.util.List;

public class FakeSMTPGateway implements SMTPGateway {

    List<Email> sentEmails = new ArrayList<>();

    @Override public void sendEmail(Email email) {
        sentEmails.add(email);
    }

    @Override public List<Email> getSentMail() {
        return sentEmails;
    }


}