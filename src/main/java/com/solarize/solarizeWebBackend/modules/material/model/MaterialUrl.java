package com.solarize.solarizeWebBackend.modules.material.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_url")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String url;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_material")
    private Material material;
}
