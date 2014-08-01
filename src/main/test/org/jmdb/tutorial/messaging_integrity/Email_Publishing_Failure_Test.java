package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.FailedToPublishException;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.email.Email;
import org.jmdb.tutorial.messaging_integrity.email.EmailAdminRepository;
import org.jmdb.tutorial.messaging_integrity.email.EmailEventPublisher;
import org.jmdb.tutorial.messaging_integrity.email.EmailRepository;
import org.jmdb.tutorial.messaging_integrity.email.EmailRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.email.EmailStatus;
import org.jmdb.tutorial.messaging_integrity.email.FailedToSendEmailException;
import org.jmdb.tutorial.messaging_integrity.email.FakeSMTPGateway;
import org.jmdb.tutorial.messaging_integrity.email.SMTPGateway;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.history.History;
import org.jmdb.tutorial.messaging_integrity.history.HistoryEvent;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.jmdb.tutorial.messaging_integrity.email.EmailStatus.SENT;
import static org.jmdb.tutorial.messaging_integrity.eventstore.StandardEventStatus.RECORDED;

public class Email_Publishing_Failure_Test {

    private HistoryRepository historyRepository;
    private EmailAdminRepository emailAdminRepository;
    private EmailRepository emailRepository;
    private String emailId;
    private Map<String, String> data;

    @Before
    public void setUp() {
        SMTPGateway smtpGateway = new FakeSMTPGateway();

        EventStore eventStore = new InMemoryEventStore();
        emailRepository = new EmailRepository(eventStore);
        emailAdminRepository = new EmailAdminRepository(eventStore);

        historyRepository = new InMemoryHistoryRepository();
        EmailEventPublisher emailEventPublisher = new FailingEmailEventPublisher("xxxx@xxxx.xxx", historyRepository);

        EmailRequestProcessor emailRequestProcessor = new EmailRequestProcessor(new AuthorisationContext(),
                                                                                eventStore,
                                                                                smtpGateway,
                                                                                emailEventPublisher);

        data = new HashMap<>();

        emailId = emailRequestProcessor.sendEmail("CUST-001", "xxxx@xxxx.xxx", "acceptance-letter", data);

    }


    @Test
    public void email_is_recorded() {
        Email email = emailRepository.get(emailId);
        assertThat(email.getCustomerId(), equalTo("CUST-001"));
        assertThat(email.getAddress(), equalTo("xxxx@xxxx.xxx"));
        assertThat(email.getTemplate(), equalTo("acceptance-letter"));
        assertThat(email.getData(), equalTo(data));

    }

    @Test
    public void emails_are_in_history() {
        History history = historyRepository.getByCustomerId("CUST-001");

        assertThat(history.getEvents().size(), equalTo(1));

        HistoryEvent historyEvent = history.getEvents().get(0);
        assertThat(historyEvent.getEventType(), equalTo("email-sent"));
    }

    @Test
    public void all_emails_are_marked_as_sent() {
        List<Email> publishedEmails = emailAdminRepository.getAllEmailsWithStatus(SENT);

        assertThat(publishedEmails.size(), equalTo(1));
    }

    private static class FailingEmailEventPublisher extends EmailEventPublisher {

        private final String emailAddressToFailOn;

        private FailingEmailEventPublisher(String emailAddressToFailOn, HistoryRepository historyRepository) {
            super(historyRepository);
            this.emailAddressToFailOn = emailAddressToFailOn;
        }

        @Override public void publishEmailSentEvent(Email email) {
            if (emailAddressToFailOn.equals(email.getAddress())) {
                throw new FailedToPublishException();
            }
            super.publishEmailSentEvent(email);
        }

    }


}