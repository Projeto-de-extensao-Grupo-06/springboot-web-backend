package com.solarize.solarizeWebBackend.modules.portfolio;

import com.solarize.solarizeWebBackend.modules.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_portfolio")
    private Long id;

    private String title;
    private String description;
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project", nullable = false)
    private Project project;
}
