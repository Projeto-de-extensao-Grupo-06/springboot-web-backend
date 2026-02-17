package com.solarize.solarizeWebBackend.modules.retryQueue;

import com.solarize.solarizeWebBackend.modules.project.Project;
import com.solarize.solarizeWebBackend.modules.project.ProjectService;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidStateTransitionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.solarize.solarizeWebBackend.modules.project.projectListener.event.ProjectStatusChangedToRetryingEvent;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryQueueProcessor {
    private final ProjectService projectService;
    private final RetryQueueService retryService;
    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedRate = 60_000)
    public void queueChecker() {
        List<RetryQueue> queue = retryService.getRetriesInQueue(2);

        if(queue.isEmpty()) return;

        this.processQueue(queue);
    }

    private void processQueue(List<RetryQueue> queue) {
        queue.forEach(retry -> {
            try {
                Project project = retry.getProject();
                projectService.setProjectStatusToRetrying(project);
                retryService.setRetryToRetrying(retry);

                publisher.publishEvent(new ProjectStatusChangedToRetryingEvent(project));
            } catch (InvalidStateTransitionException ex) {
                log.error(ex.getMessage());
            }
        });
    }
}
