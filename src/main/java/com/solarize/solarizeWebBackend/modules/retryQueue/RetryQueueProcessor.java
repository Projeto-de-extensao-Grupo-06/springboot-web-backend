package com.solarize.solarizeWebBackend.modules.retryQueue;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectRepository;
import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;
import com.solarize.solarizeWebBackend.shared.communication.whatsApp.WhatsAppService;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
            try {
                Project project = retry.getProject();
                project.getStatus().getStateHandler().applyToRetrying(project);
                projectRepository.save(project);

                retry.setRetrying(true);
                retryRepository.save(retry);

                publisher.publishEvent(new ProjectStatusChangedToRetryingEvent(project));
            } catch (InvalidStateTransitionException ex) {
                log.error(ex.getMessage());
            }
        });
    }


}
