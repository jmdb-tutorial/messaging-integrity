package org.jmdb.tutorial.messaging_integrity.history;

import org.jmdb.tutorial.messaging_integrity.applications.ApplicationHistoryRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryRepository implements ApplicationHistoryRepository {

    private final Map<String, History> histories = new HashMap<>();

    @Override public History getByApplicationId(String applicationId) {
        return histories.get(applicationId);
    }


    @Override public void put(History history) {
        histories.put(history.getApplicationId(), history);
    }
}