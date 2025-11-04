package com.solarize.solarizeWebBackend.modules.materialCatalog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.solarize.solarizeWebBackend.modules.materialUrl.MaterialUrl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "material")
public class MaterialCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MetricEnum metric;

    private String description;
    private String supplier;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MaterialUrl> urls = new ArrayList<>();

    public void addUrl(MaterialUrl url){
        urls.add(url);
        url.setMaterial(this);
    }

    public void removeUrl(MaterialUrl url){
        urls.remove(url);
        url.setMaterial(null);
    }

}
