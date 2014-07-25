package org.jmdb.tutorial.messaging_resilience;

public interface ApplicationRepository {
    Application getById(String id);

    void put(Application application);
}