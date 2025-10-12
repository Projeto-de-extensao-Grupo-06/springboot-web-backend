package com.solarize.solarizeWebBackend.modules.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_addres")
    private Integer id;

    private String postalCode;
    private String streetName;
    private String number;
    private String city;
    private String state;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;


}
