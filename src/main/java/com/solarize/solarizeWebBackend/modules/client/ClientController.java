package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService SERVICE;

    public ClientController(ClientService service){
        this.SERVICE = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable int id){
        final ClientResponseDTO client = SERVICE.getClient(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getClients(){
        final List<ClientResponseDTO> clients = SERVICE.getClients();
        return ResponseEntity.ok(clients);
    }

}
