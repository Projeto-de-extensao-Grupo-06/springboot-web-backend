package com.solarize.solarizeWebBackend.modules.retryQueue;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;
import com.solarize.solarizeWebBackend.shared.communication.whatsApp.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetryQueueProcessor {
    private final RetryQueueRepository retryRepository;
    private final ProjectRepository projectRepository;
    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedRateString = "${retry.schedule.rate}")
    public void queueChecker() {
        List<RetryQueue> queue = retryRepository
                .getAllByScheduledDateBeforeAndRetryingIsFalse(LocalDateTime.now().minusMinutes(1));

        if(queue.isEmpty()) return;

        this.processQueue(queue);
    }

    private void processQueue(List<RetryQueue> queue) {
        queue.forEach(retry -> {
            Project project = retry.getProject();
            project.getStatus().getStateHandler().applyToRetrying(project);
            projectRepository.save(project);

            retry.setRetrying(true);
            retryRepository.save(retry);

            publisher.publishEvent(new ProjectStatusChangedToRetryingEvent(project));
        });
    }


}
