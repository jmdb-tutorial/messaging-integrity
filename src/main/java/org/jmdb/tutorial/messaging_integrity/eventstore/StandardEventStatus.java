package org.jmdb.tutorial.messaging_integrity.eventstore;

public enum StandardEventStatus implements EventStatus {
    RECORDED("recorded"),
    PUBLISHED("published");

    private final String id;

    StandardEventStatus(String id) {this.id = id;}

    @Override public String getId() {
        return id;
    }
}