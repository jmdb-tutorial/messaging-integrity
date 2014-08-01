package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.History;

public class Application {

    public final String id;
    public final String customerId;

    public Application(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

}