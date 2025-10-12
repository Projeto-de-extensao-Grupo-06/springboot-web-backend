package com.solarize.solarizeWebBackend.project;

import com.solarize.solarizeWebBackend.budget.Budget;
import com.solarize.solarizeWebBackend.client.Client;
import com.solarize.solarizeWebBackend.modules.address.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum status;

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
    private ProjectSourceEnum projectSource;

    private String name;
    private String description;
}
