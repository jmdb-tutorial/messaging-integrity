package org.jmdb.tutorial.messaging_integrity.email;

public enum EmailStatus {
    RECORDED("recorded"),
    SENT("sent"),
    PUBLISHED("published");

    private final String id;


    EmailStatus(String id) {
        this.id = id;
    }
}