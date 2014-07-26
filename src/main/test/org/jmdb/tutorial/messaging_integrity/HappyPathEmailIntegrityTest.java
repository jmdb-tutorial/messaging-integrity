package org.jmdb.tutorial.messaging_integrity;

import org.jmdb.tutorial.messaging_integrity.email.Email;
import org.jmdb.tutorial.messaging_integrity.email.EmailEventPublisher;
import org.jmdb.tutorial.messaging_integrity.email.EmailRepository;
import org.jmdb.tutorial.messaging_integrity.email.EmailRequestProcessor;
import org.jmdb.tutorial.messaging_integrity.email.EmailStatus;
import org.jmdb.tutorial.messaging_integrity.email.FakeSMTPGateway;
import org.jmdb.tutorial.messaging_integrity.email.InMemoryEmailRepository;
import org.jmdb.tutorial.messaging_integrity.email.SMTPGateway;
import org.jmdb.tutorial.messaging_integrity.history.HistoryRepository;
import org.jmdb.tutorial.messaging_integrity.history.InMemoryHistoryRepository;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jmdb.tutorial.messaging_integrity.email.EmailStatus.PUBLISHED;
import static org.jmdb.tutorial.messaging_integrity.email.EmailStatus.RECORDED;

public class HappyPathEmailIntegrityTest {

    private HistoryRepository historyRepository;

    @Test
    public void sends_email() {
        SMTPGateway smtpGateway = new FakeSMTPGateway();

        EmailRepository emailRepository = new InMemoryEmailRepository();

        historyRepository = new InMemoryHistoryRepository();
        EmailEventPublisher emailEventPublisher = new EmailEventPublisher(historyRepository);

        EmailRequestProcessor emailRequestProcessor = new EmailRequestProcessor(emailRepository, smtpGateway,
                                                                                emailEventPublisher);

        Map<String, String> data = new HashMap<>();

        String emailId = emailRequestProcessor.sendEmail("xxxx@xxxx.xxx", "acceptance-letter", data);

        Email email = emailRepository.get(emailId);
        assertThat(email.getAddress(), equalTo("xxxx@xxxx.xxx"));
        assertThat(email.getTemplate(), equalTo("acceptance-letter"));
        assertThat(email.getData(), equalTo(data));

        List<Email> failedEmails = emailRepository.getAllEmailsWithStatus(RECORDED);

        assertThat(failedEmails.size(), equalTo(1));
    }


}