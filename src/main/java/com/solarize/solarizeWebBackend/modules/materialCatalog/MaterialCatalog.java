package com.solarize.solarizeWebBackend.modules.materialCatalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MaterialCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer id;

    private String name;
    private Double price;
    private String supplier;
    private String url;

    @Enumerated(EnumType.STRING)
    private MetricEnum metric;

}
