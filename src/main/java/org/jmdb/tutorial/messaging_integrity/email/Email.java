package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Email {
    private String customerId;
    private final String address;
    private final String template;
    private final Map<String, String> data;

    public Email(String customerId, String address, String template, Map<String, String> data) {
        this.customerId = customerId;
        this.address = address;
        this.template = template;
        this.data = data;
    }


    public String getAddress() {
        return address;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getCustomerId() {
        return customerId;
    }
}