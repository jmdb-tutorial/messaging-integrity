package org.jmdb.tutorial.messaging_integrity.history;

import org.jmdb.tutorial.messaging_integrity.history.History;

public interface HistoryRepository {
    History getByApplicationId(String applicationId);

    void put(History history);
}