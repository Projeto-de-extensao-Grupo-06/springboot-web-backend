package com.solarize.solarizeWebBackend.modules.schedule;

import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule")
    private Long id;

    private LocalTime notificationAlertTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project", nullable = true)
    private Project project;

    @Enumerated(EnumType.STRING)
    private ScheduleTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_coworker", nullable = false)
    private Coworker coworker;

    @Enumerated(EnumType.STRING)
    private ScheduleStatusEnum status = ScheduleStatusEnum.MARKED;

    private String title;

    private String description;
}
