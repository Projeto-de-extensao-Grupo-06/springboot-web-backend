package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.budget.Budget;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum status;
    private Integer statusWeight;

    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum previewStatus;

    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_client", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_budget")
    private Budget budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_address")
    private Address address;

    private LocalDateTime createdAt;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private SystemTypeEnum systemType;

    @Enumerated(EnumType.STRING)
    private ProjectSourceEnum projectFrom;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectFile> files = new ArrayList<>();

    private Integer retryAttemps;

    @PrePersist
    @PreUpdate
    public void updateStatusWeight() {
        if(this.status != null) {
            this.statusWeight = this.status.getWeight();
        }
    }
}
