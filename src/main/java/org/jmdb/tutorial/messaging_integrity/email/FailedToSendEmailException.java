package org.jmdb.tutorial.messaging_integrity.email;

import static java.lang.String.format;

public class FailedToSendEmailException extends RuntimeException {

    public FailedToSendEmailException(String emailAddress) {
        super(format("Failed to send email to [%s]", emailAddress));
    }
}