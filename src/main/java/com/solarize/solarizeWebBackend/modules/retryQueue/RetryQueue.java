package com.solarize.solarizeWebBackend.modules.retryQueue;

import com.solarize.solarizeWebBackend.modules.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RetryQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledDate;
    private Boolean retrying;

    @OneToOne
    @JoinColumn(name = "fk_project")
    private Project project;
}
