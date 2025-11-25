package com.solarize.solarizeWebBackend.modules.material.model;

import com.solarize.solarizeWebBackend.modules.material.MetricEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private MetricEnum metric;

}
