package org.jmdb.tutorial.messaging_integrity.email;

import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.eventstore.DataEvent;
import org.jmdb.tutorial.messaging_integrity.eventstore.Event;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStream;

import java.util.List;

public class EmailRepository {

    private final EventStore eventStore;

    public EmailRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    public Email get(String emailId) {
        return ((DataEvent<Email>)eventStore.getEventById(emailId)).getData();
    };


}