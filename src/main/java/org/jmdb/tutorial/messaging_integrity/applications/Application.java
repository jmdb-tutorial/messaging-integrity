package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.History;

public class Application {
    private History history;
    private String id;

    public Application(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}