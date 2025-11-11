package com.solarize.solarizeWebBackend.modules.RetryConfig;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RetryConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_retry_config")
    private Long id;

    private Integer preBudget;
    private Integer finalBudget;
    private Integer


}
