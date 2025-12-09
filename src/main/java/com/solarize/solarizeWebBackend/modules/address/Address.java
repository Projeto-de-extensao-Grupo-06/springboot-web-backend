package com.solarize.solarizeWebBackend.modules.address;

import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.enumerated.TypeEnum;
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

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String streetName;

    private String number;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrazilianState state;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    private String apartment;


}
