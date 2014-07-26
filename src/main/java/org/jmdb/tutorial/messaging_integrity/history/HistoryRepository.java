package org.jmdb.tutorial.messaging_integrity.history;

import org.jmdb.tutorial.messaging_integrity.history.History;

public interface HistoryRepository {
    History getByCustomerId(String applicationId);

    void put(History history);
}