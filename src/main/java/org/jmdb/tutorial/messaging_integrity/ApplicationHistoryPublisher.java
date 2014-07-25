package org.jmdb.tutorial.messaging_integrity;

public class ApplicationHistoryPublisher {
    private ApplicationHistoryRepository applicationHistoryRepository;

    public ApplicationHistoryPublisher(ApplicationHistoryRepository applicationHistoryRepository) {
        this.applicationHistoryRepository = applicationHistoryRepository;
    }

    public void publishApplicationCreated(Application application) {
        ApplicationHistory history = new ApplicationHistory(application.getId());
        history.addEvent(new ApplicationHistory.CreatedEvent(application.getId()));
        applicationHistoryRepository.put(history);

    }
}