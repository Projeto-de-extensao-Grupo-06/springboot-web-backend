package com.solarize.solarizeWebBackend.modules.project;
import com.solarize.solarizeWebBackend.modules.address.AddressDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.coworker.dtos.CoworkerResponseDto;
import com.solarize.solarizeWebBackend.modules.project.dto.ProjectSummaryDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository projectRepository;


    public Page<ProjectSummaryDTO> findAllProjectsSummary(String search,
                                                          List<ProjectStatusEnum> statusList,
                                                          Long responsibleId,
                                                          Long clientId,
                                                          Pageable pageable) {

        Page<Project> projects = projectRepository.findAllProjects(search, statusList, responsibleId, clientId, pageable);

        if (projects.isEmpty()) {
            throw new BadRequestException("Nenhum projeto encontrado com os filtros informados.");
        }

        return projects.map(p -> {
            ProjectSummaryDTO dto = ProjectMapper.toSummary(p);
            dto.setNextSchedule(projectRepository.buscarProximoSchedule(p.getId()));
            dto.setCommentCount(projectRepository.contarComentarios(p.getId()));
            dto.setFileCount(projectRepository.contarArquivos(p.getId()));
            return dto;
        });
    }



}
