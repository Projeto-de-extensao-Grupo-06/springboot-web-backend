package com.solarize.solarizeWebBackend.modules.retryQueue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetryQueueService {
    private final RetryQueueRepository repository;

    public List<RetryQueue> getRetriesInQueue(Integer minusMinutes) {
        return repository.getAllByScheduledDateBeforeAndRetryingIsFalse(LocalDateTime.now().minusMinutes(minusMinutes));
    }

    public void setRetryToRetrying (RetryQueue retry) {
        retry.setRetrying(true);
        repository.save(retry);
    }
}
