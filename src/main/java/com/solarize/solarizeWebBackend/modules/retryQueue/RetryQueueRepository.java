package com.solarize.solarizeWebBackend.modules.retryQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.time.LocalDateTime;

public interface RetryQueueRepository extends JpaRepository<RetryQueue, Long> {
    @Query("""
        SELECT r FROM RetryQueue r 
        WHERE r.scheduledDate < :date 
            AND r.retrying = false 
            AND r.project.isActive = true
    """)
    List<RetryQueue> getAllByScheduledDateBeforeAndRetryingIsFalse(@Param("date") LocalDateTime date);
}
