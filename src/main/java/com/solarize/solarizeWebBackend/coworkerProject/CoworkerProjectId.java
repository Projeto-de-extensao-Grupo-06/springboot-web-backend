package com.solarize.solarizeWebBackend.coworkerProject;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CoworkerProjectId implements Serializable {
    private Integer coworker;
    private Integer project;
}
