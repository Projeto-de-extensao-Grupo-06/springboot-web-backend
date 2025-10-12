package com.solarize.solarizeWebBackend.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Integer id;


    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String cnpj;

    private String note;
    private String phone;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_coworker_last_update")
    private Coworker coworkerLastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_main_address")
    private Address mainAddress;
}
