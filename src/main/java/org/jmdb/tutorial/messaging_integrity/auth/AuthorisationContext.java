package org.jmdb.tutorial.messaging_integrity.auth;

public class AuthorisationContext {

    public User getCurrentUser() {
        return new User("xxxxxx", "Xx. Xxxxx Xxxxx");
    }

    public String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}