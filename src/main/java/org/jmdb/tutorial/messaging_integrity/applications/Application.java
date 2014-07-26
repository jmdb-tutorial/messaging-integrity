package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.History;

public class Application {

    private final String id;
    private final String customerId;

    public Application(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }
}