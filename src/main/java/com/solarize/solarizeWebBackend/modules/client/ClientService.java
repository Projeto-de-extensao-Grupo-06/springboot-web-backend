package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository REPOSITORY;

    public ClientResponseDTO getClient(int id){
        Optional<Client> client = REPOSITORY.findById(id);
        if(client.isEmpty()) throw new NotFoundException("User not found.");
        return ClientMapper.of(client.get());
    }

    public List<ClientResponseDTO> getClients(){
        List<Client> clients = REPOSITORY.findAll();
        return ClientMapper.of(clients);
    }
}
