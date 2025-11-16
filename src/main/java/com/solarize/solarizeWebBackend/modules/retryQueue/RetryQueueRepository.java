package com.solarize.solarizeWebBackend.modules.retryQueue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RetryQueueRepository extends JpaRepository<RetryQueue, Long> {
}
