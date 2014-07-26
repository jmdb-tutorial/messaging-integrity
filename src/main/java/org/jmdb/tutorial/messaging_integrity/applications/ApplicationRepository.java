package org.jmdb.tutorial.messaging_integrity.applications;

public interface ApplicationRepository {
    Application getById(String id);

    void put(Application application);
}