package com.solarize.solarizeWebBackend.shared.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    public void scheduleTask(String taskName, Runnable task, LocalDateTime dateTime) {
        Instant instant = dateTime
                .atZone(ZoneId.systemDefault())
                .toInstant();

        ScheduledFuture<?> future = taskScheduler.schedule(task, instant);
        scheduledTasks.put(taskName, future);
    }


    public boolean cancelTask(String taskName) {
        ScheduledFuture<?> future = scheduledTasks.remove(taskName);
        if (future != null) {
            return future.cancel(false);
        }
        return false;
    }

}
