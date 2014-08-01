package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.applications.FailedToPublishException;
import org.jmdb.tutorial.messaging_integrity.auth.AuthorisationContext;
import org.jmdb.tutorial.messaging_integrity.email.Email;
import org.jmdb.tutorial.messaging_integrity.email.EmailAdminRepository;
import org.jmdb.tutorial.messaging_integrity.email.EmailEventQueue;
import org.jmdb.tutorial.messaging_integrity.email.EmailRepository;
import org.jmdb.tutorial.messaging_integrity.email.EmailRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.email.FakeSMTPGateway;
import org.jmdb.tutorial.messaging_integrity.email.SMTPGateway;
import org.jmdb.tutorial.messaging_integrity.eventstore.EventStore;
import org.jmdb.tutorial.messaging_integrity.eventstore.InMemoryEventStore;
import org.jmdb.tutorial.messaging_integrity.history.History;
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
        EmailEventQueue emailEventQueue = new FailingEmailEventQueue("xxxx@xxxx.xxx", historyRepository);

        EmailRequestProcessor emailRequestProcessor = new EmailRequestProcessor(new AuthorisationContext(),
                                                                                eventStore,
                                                                                smtpGateway,
                                                                                emailEventQueue);

        data = new HashMap<>();

        emailId = emailRequestProcessor.sendEmail("CUST-001", "xxxx@xxxx.xxx", "acceptance-letter", data);

    }


    @Test
    public void email_is_recorded() {
        Email email = emailRepository.get(emailId);
        assertThat(email.customerId, equalTo("CUST-001"));
        assertThat(email.address, equalTo("xxxx@xxxx.xxx"));
        assertThat(email.template, equalTo("acceptance-letter"));
        assertThat(email.data, equalTo(data));

    }

    @Test
    public void emails_are_not_in_history() {
        History history = historyRepository.getByCustomerId("CUST-001");

        assertThat(history, nullValue());
    }

    @Test
    public void all_emails_are_marked_as_sent() {
        List<Email> publishedEmails = emailAdminRepository.getAllEmailsWithStatus(SENT);

        assertThat(publishedEmails.size(), equalTo(1));
    }

    private static class FailingEmailEventQueue extends EmailEventQueue {

        private final String emailAddressToFailOn;

        private FailingEmailEventQueue(String emailAddressToFailOn, HistoryRepository historyRepository) {
            super(historyRepository);
            this.emailAddressToFailOn = emailAddressToFailOn;
        }

        @Override public void publishEmailSent(Email email) {
            if (emailAddressToFailOn.equals(email.address)) {
                throw new FailedToPublishException();
            }
            super.publishEmailSent(email);
        }

    }


}