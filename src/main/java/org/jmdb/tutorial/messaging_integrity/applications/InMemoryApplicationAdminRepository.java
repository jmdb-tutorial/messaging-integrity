package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.eventstore.Event;

import java.util.List;

public class InMemoryApplicationAdminRepository implements ApplicationAdminRepository {
    private List<Event> unpublishedEvents;

    public List<Event> getUnpublishedEvents() {
        return unpublishedEvents;
    }
}