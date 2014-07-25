package org.jmdb.tutorial.messaging_integrity;

public interface ApplicationHistoryRepository {
    ApplicationHistory getByApplicationId(String s);

    void put(ApplicationHistory history);
}