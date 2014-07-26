package org.jmdb.tutorial.messaging_integrity.applications;

public class CreateApplicationRequestBuilder {
    private String id;

    public static CreateApplicationRequestBuilder createApplicationRequest() {
        return new CreateApplicationRequestBuilder();
    }

    public CreateApplicationRequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public CreateApplicationRequest build() {
        return new CreateApplicationRequest(id);
    }

}