package org.jmdb.tutorial.messaging_integrity.auth;

public class AuthorisationContext {

    public User currentUser() {
        return new User("xxxxxx", "Xx. Xxxxx Xxxxx");
    }

    public String currentUserId() {
        return currentUser().userId;
    }
}