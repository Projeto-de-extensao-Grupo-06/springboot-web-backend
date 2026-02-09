package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String note;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_coworker_last_update")
    private Coworker coworkerLastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_main_address")
    private Address mainAddress;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = ClientStatusEnum.ACTIVE;
        }
    }
}
