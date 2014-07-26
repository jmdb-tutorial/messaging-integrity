package org.jmdb.tutorial.messaging_integrity.auth;

public class User {

    private final String userId;
    private final String displayName;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}