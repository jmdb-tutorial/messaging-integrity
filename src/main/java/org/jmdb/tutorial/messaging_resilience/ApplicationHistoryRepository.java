package org.jmdb.tutorial.messaging_resilience;

public interface ApplicationHistoryRepository {
    ApplicationHistory getByApplicationId(String s);
}