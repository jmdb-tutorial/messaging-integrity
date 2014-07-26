package org.jmdb.tutorial.messaging_integrity.applications;

import org.jmdb.tutorial.messaging_integrity.history.History;

public interface ApplicationHistoryRepository {
    History getByApplicationId(String s);

    void put(History history);
}