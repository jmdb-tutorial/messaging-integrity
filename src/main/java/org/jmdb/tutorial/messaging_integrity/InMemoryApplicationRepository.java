package org.jmdb.tutorial.messaging_integrity;

import java.util.HashMap;
import java.util.Map;

public class InMemoryApplicationRepository implements ApplicationRepository {

    private final Map<String, Application> dataStore = new HashMap<>();

    @Override public Application getById(String id) {
        return dataStore.get(id);
    }

    @Override public void put(Application application) {
        dataStore.put(application.getId(), application);
    }
}