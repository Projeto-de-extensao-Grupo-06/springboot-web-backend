package com.solarize.solarize_web_backend.modules.material_catalog;

import jakarta.persistence.Entity;

@Entity
public class MaterialCatalogEntity {
    private Integer id_material;
    private Integer name;
    private Double price;
    private String supplier;
    private String url;

}
