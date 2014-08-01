package org.jmdb.tutorial.messaging_integrity.platform.eventstore;

public interface Event {

    String getUserId();
    String getTimeStamp();
    EventStatus getEventStatus();

    Event changeStatusTo(EventStatus eventStatus);

    String getId();



}