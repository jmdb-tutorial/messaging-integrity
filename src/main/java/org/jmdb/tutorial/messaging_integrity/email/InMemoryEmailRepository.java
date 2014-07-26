package org.jmdb.tutorial.messaging_integrity.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEmailRepository implements EmailRepository {

    private final Map<String, Email> emailStore = new HashMap<>();

    @Override public Email get(String emailId) {
        return emailStore.get(emailId);
    }

    @Override public List<Email> getAllEmailsWithStatus(EmailStatus emailStatus) {
        List<Email> matchingEmails = new ArrayList<>();
        for (Email email : emailStore.values()) {
            if (emailStatus.equals(email.getEmailStatus())) {
                matchingEmails.add(email);
            }
        }
        return matchingEmails;
    }

    @Override public void put(Email email) {
        emailStore.put(email.getId(), email);
    }
}