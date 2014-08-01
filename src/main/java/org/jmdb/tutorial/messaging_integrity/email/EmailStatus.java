package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.eventstore.EventStatus;

public enum EmailStatus implements EventStatus {
    SENT("sent");

    private final String id;


    EmailStatus(String id) {
        this.id = id;
    }


    @Override public String getId() {
        return id;
    }
}