package com.solarize.solarizeWebBackend.modules.retryQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.time.LocalDateTime;

public interface RetryQueueRepository extends JpaRepository<RetryQueue, Long> {
    List<RetryQueue> getAllByScheduledDateBeforeAndRetryingIsFalse(LocalDateTime localDateTime);
}
