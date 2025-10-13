package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.shared.exceptions.ClientNotFoundException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class ClientService {

    private final ClientRepository REPOSITORY;

    public ClientService(ClientRepository repository){
        this.REPOSITORY = repository;
    }

    public ClientResponseDTO getClient(int id){
        Optional<Client> client = REPOSITORY.findById(id);
        if(client.isEmpty()) throw new ClientNotFoundException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        return ClientMapper.of(client.get());
    }

    public List<ClientResponseDTO> getClients(){
        List<Client> clients = REPOSITORY.findAll();
        return ClientMapper.of(clients);
    }
}
