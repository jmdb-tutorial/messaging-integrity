package org.jmdb.tutorial.messaging_integrity.eventstore;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class EventStoreTest {

    private Event eventAsRecorded;
    private EventStream eventStream;

    @Before
    public void setUp() {
        EventStore eventStore = new InMemoryEventStore();

        eventStream = eventStore.eventStreamFor("DOMAIN-ID-01");
        eventAsRecorded = eventStream.storeEvent("USER-01", "some-event-type", new TestData("some data"));
    }

    @Test
    public void has_status_of_recorded_when_first_stored() {
        assertThat(eventAsRecorded.getEventStatus(), sameInstance((EventStatus)RECORDED));
    }

    @Test
    public void can_change_status() {
        eventStream.updateStatusOfEvent(eventAsRecorded.getId(), CustomEventStatus.EMAIL_SENT);

        Event lastEvent = eventStream.getLastEvent();

        assertThat(lastEvent.getEventStatus(), sameInstance((EventStatus)CustomEventStatus.EMAIL_SENT));
    }

    private static enum CustomEventStatus implements EventStatus {
        EMAIL_SENT("email-sent");

        private final String id;
        CustomEventStatus(String id) {
            this.id = id;
        }

        @Override public String getId() {
            return id;
        }
    }

    private static class TestData {

        private final String data;

        private TestData(String data) {this.data = data;}


        public String getData() {
            return data;
        }
    }
}