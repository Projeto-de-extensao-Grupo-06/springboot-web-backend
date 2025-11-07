package com.solarize.solarizeWebBackend.modules.coworkerProject;


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
