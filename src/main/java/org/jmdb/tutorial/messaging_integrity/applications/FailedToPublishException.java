package org.jmdb.tutorial.messaging_integrity.applications;

public class FailedToPublishException extends RuntimeException {
    public FailedToPublishException() {
        super("We failed to reach the messagebroker!");
    }
}