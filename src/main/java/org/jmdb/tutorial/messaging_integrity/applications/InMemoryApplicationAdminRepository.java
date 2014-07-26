package org.jmdb.tutorial.messaging_integrity.applications;

import java.util.BitSet;

public class InMemoryApplicationAdminRepository implements ApplicationAdminRepository {
    private BitSet unpublishedEvents;

    public List<> getUnpublishedEvents() {
        return unpublishedEvents;
    }
}