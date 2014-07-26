package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStatus;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;

import java.util.ArrayList;
import java.util.List;

public class EmailAdminRepository {
    private EventStore eventStore;

    public EmailAdminRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public List<Email> getAllEmailsWithStatus(EventStatus eventStatus) {
        List<Event> events = eventStore.getAllEventsWithStatus(eventStatus);

        List<Email> emails = new ArrayList<>();

        for (Event event : events) {
            emails.add(((DataEvent<Email>)event).getData());
        }

        return  emails;
    }
}