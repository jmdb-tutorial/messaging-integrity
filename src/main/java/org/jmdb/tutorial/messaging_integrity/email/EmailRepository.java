package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.platform.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.platform.eventstore.EventStore;

public class EmailRepository {

    private final EventStore eventStore;

    public EmailRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    public Email get(String emailId) {
        return ((DataEvent<Email>)eventStore.getEventById(emailId)).getData();
    };


}