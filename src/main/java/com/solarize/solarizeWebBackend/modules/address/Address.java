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
    @Column(name = "id_address")
    private Long id;

    private String postalCode;
    private String streetName;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;


}
