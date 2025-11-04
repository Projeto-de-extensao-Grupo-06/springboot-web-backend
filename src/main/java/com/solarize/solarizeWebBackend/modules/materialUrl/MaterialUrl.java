package com.solarize.solarizeWebBackend.modules.materialUrl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solarize.solarizeWebBackend.modules.materialCatalog.MaterialCatalog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "material_url")
public class MaterialUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_url")
    private Long id;

    private String description;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_material", nullable = false)
    @JsonBackReference
    private MaterialCatalog material;

}
