package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository REPOSITORY;

    public ClientResponseDTO getClient(Long id){
        Optional<Client> client = REPOSITORY.findById(id);
        if(client.isEmpty()) throw new NotFoundException("Client not found.");
        return ClientMapper.of(client.get());
    }

    public List<ClientResponseDTO> getClients(){
        List<Client> clients = REPOSITORY.findAll();
        return ClientMapper.of(clients);
    }

    public ClientResponseDTO postClient(CreateClientDTO dto){
        validateConflict(dto);
        Client client = ClientMapper.of(dto);
        client = REPOSITORY.save(client);
        return ClientMapper.of(client);
    }

    private void validateConflict(CreateClientDTO dto) {
        if (REPOSITORY.existsByDocumentNumber(dto.getDocumentNumber())) throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmail(dto.getEmail())) throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhone(dto.getPhone())) throw new ConflictException("Phone already exists");

        if (dto.getCnpj() != null && !dto.getCnpj().isBlank()) {
            if (REPOSITORY.existsByCnpj(dto.getCnpj())) throw new ConflictException("CNPJ already exists");
        }
    }
}
