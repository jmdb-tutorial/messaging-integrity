package org.jmdb.tutorial.messaging_integrity.email;

import java.util.Map;

public class Email {
    public String customerId;
    public final String address;
    public final String template;
    public final Map<String, String> data;

    public Email(String customerId, String address, String template, Map<String, String> data) {
        this.customerId = customerId;
        this.address = address;
        this.template = template;
        this.data = data;
    }


}