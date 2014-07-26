package org.jmdb.tutorial.messaging_integrity.email;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Email {
    private String id;
    private final String address;
    private final String template;
    private final Map<String, String> data;
    private final EmailStatus emailStatus;

    public Email(String id, String address, String template, Map<String, String> data) {
        this.id = id;
        this.address = address;
        this.template = template;
        this.data = data;
        this.emailStatus = EmailStatus.RECORDED;
    }

    private Email(String id, String address, String template,
                  HashMap<String, String> data,
                  EmailStatus emailStatus) {


        this.id = id;
        this.address = address;
        this.template = template;
        this.data = data;
        this.emailStatus = emailStatus;
    }

    public Email changeEmailStatus(EmailStatus newStatus) {
        return new Email(id, address, template, new HashMap<>(data), newStatus);
    }


    public String getId() {
        return id;
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

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }
}