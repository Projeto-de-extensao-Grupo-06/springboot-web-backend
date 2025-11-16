package com.solarize.solarizeWebBackend.associationEntities.coworkerProject;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CoworkerProjectId implements Serializable {
    private Long coworker;
    private Long project;
}
