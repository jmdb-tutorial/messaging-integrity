package org.jmdb.tutorial.messaging_resilience;

public class Application {
    private ApplicationHistory history;
    private String id;

    public Application(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}