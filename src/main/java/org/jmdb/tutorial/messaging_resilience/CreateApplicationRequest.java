package org.jmdb.tutorial.messaging_resilience;

public class CreateApplicationRequest {
    private final String id;

    public CreateApplicationRequest(String id) {
        this.id = id;
    }

    public Application toApplication() {
        return new Application(id);
    }

    public String getId() {
        return id;
    }
}