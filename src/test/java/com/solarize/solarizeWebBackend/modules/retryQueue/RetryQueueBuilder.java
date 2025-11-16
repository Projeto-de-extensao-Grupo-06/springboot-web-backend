package com.solarize.solarizeWebBackend.modules.retryQueue;

import com.solarize.solarizeWebBackend.modules.project.Project;

import java.time.LocalDateTime;

public class RetryQueueBuilder {
    private Long id;
    private LocalDateTime scheduledDate;
    private Boolean retrying;
    private Project project;

    private RetryQueueBuilder() {}

    public static RetryQueueBuilder builder() {
        return new RetryQueueBuilder()
                .withId(1L)
                .withScheduledDate(LocalDateTime.now().plusHours(1))
                .withRetrying(false)
                .withProject(null);
    }

    public RetryQueueBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RetryQueueBuilder withScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
        return this;
    }

    public RetryQueueBuilder withRetrying(Boolean retrying) {
        this.retrying = retrying;
        return this;
    }

    public RetryQueueBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    public RetryQueueBuilder withFutureDate() {
        this.scheduledDate = LocalDateTime.now().plusHours(1);
        return this;
    }

    public RetryQueueBuilder withPastDate() {
        this.scheduledDate = LocalDateTime.now().minusHours(1);
        return this;
    }

    public RetryQueue build() {
        RetryQueue retryQueue = new RetryQueue();
        retryQueue.setId(this.id);
        retryQueue.setScheduledDate(this.scheduledDate);
        retryQueue.setRetrying(this.retrying);
        retryQueue.setProject(this.project);
        return retryQueue;
    }
}
