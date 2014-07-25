package org.jmdb.tutorial.messaging_resilience;

import java.util.HashMap;
import java.util.Map;

public class InMemoryApplicationRepository implements ApplicationRepository {

    private final Map<String, Application> dataStore = new HashMap<>();

    @Override public Application getById(String id) {
        return null;
    }

    @Override public void put(Application application) {
        dataStore.put(application.getId(), application);
    }
}