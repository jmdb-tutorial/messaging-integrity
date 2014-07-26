package org.jmdb.tutorial.messaging_integrity.history;

import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryRepository implements HistoryRepository {

    private final Map<String, History> histories = new HashMap<>();

    @Override public History getByApplicationId(String applicationId) {
        return histories.get(applicationId);
    }


    @Override public void put(History history) {
        histories.put(history.getApplicationId(), history);
    }
}