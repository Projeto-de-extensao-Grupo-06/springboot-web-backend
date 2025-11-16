package com.solarize.solarizeWebBackend.modules.project;


import com.solarize.solarizeWebBackend.modules.project.dto.ProjectPageDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    private static final int pageSize = 20;
    private static final List<ProjectStatusEnum> HIDDEN_STATUS = Arrays.asList( ProjectStatusEnum.AWAITING_RETRY,  ProjectStatusEnum.NEGOTIATION_FAILED, ProjectStatusEnum.COMPLETED);

 

}
