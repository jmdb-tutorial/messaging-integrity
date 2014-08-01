package org.jmdb.tutorial.messaging_integrity.applications;

public class CreateApplicationRequest {
    public final String id;
    public final String customerId;

    public CreateApplicationRequest(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public Application toApplication() {
        return new Application(id, customerId);
    }

}