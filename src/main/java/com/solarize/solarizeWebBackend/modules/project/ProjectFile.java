package com.solarize.solarizeWebBackend.modules.project;


import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ProjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project_file")
    private Long id;

    private String filename;

    private String originalFilename;

    private LocalDateTime createdAt;

    private Integer mbSize;

    private String checkSum;

    private Boolean homologationDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_coworker")
    private Coworker uploader;
}
