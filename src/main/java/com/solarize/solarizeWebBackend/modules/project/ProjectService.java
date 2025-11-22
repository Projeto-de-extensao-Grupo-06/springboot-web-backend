package com.solarize.solarizeWebBackend.modules.project;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.Client;
import com.solarize.solarizeWebBackend.modules.client.ClientRepository;
import com.solarize.solarizeWebBackend.modules.project.dto.CreateProjectRequestDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public Project createManualProject(CreateProjectRequestDTO requestDTO){

        if (projectRepository.existsByProjectName(requestDTO.getName())){
            throw new ConflictException("Project name already exists");
        }

        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: %d".formatted(requestDTO.getClientId())));

        Address address = null;
        if (requestDTO.getAddressId() != null){
            address = addressRepository.findById(requestDTO.getAddressId()).orElse(null);
        } else if (client.getMainAddress() != null) {
            address = client.getMainAddress();
        }

        Project project = new Project();
        project.setName(requestDTO.getName());
        project.setClient(client);
        project.setAddress(address);
        project.setDescription(requestDTO.getDescription());
        project.setSystemType(requestDTO.getSystemTypeEnum());
        project.setProjectFrom(ProjectSourceEnum.INTERNAL_MANUAL_ENTRY);
        project.setStatus(ProjectStatusEnum.NEW);
        project.setCreatedAt(LocalDateTime.now());
        project.setIsActive(true);

        return projectRepository.save(project);


    }


}
