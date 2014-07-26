package org.jmdb.tutorial.messaging_integrity.email;

import java.util.List;

public interface EmailRepository {
    Email get(String emailId);

    List<Email> getAllEmailsWithStatus(EmailStatus emailStatus);

    void put(Email email);
}