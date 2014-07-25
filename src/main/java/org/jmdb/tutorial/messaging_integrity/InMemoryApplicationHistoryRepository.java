package org.jmdb.tutorial.messaging_integrity;

import java.util.HashMap;
import java.util.Map;

public class InMemoryApplicationHistoryRepository implements ApplicationHistoryRepository {

    private final Map<String, ApplicationHistory> histories = new HashMap<>();

    @Override public ApplicationHistory getByApplicationId(String s) {
        return null;
    }


    @Override public void put(ApplicationHistory history) {
        histories.put(history.getApplicationId(), history);
    }
}