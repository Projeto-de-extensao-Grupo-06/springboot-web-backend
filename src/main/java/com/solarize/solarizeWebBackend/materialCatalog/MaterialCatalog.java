package com.solarize.solarizeWebBackend.materialCatalog;

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

    private Integer name;
    private Double price;
    private String supplier;
    private String url;

    @Enumerated(EnumType.STRING)
    private MetricEnum metric;

}
