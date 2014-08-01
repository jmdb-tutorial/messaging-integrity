package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.platform.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;

public class EmailRepository {

    private final EventStore eventStore;

    public EmailRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    public Email get(String emailId) {
        Event<Email> event = eventStore.getEventById(emailId);
        return event.getPayload();
    };


}