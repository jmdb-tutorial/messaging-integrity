package org.jmdb.tutorial.messaging_integrity.applications;

public class FailedToPublishException extends RuntimeException {
    public FailedToPublishException() {
        super("BANG! we failed to reach the messagebroker!");
    }
}