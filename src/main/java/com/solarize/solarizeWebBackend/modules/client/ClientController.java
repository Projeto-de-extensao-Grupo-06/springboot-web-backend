package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService SERVICE;

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(
            @PathVariable int id
    ){
        final ClientResponseDTO client = SERVICE.getClient(id);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getClients(){
        final List<ClientResponseDTO> clients = SERVICE.getClients();
        HttpStatus status = HttpStatus.OK;

        if(clients.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clients);
    }

    @PreAuthorize("hasAuthority('CLIENT_WRITE')")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> postClient(
            @Valid @RequestBody CreateClientDTO client
    ){
        final ClientResponseDTO createdClient = SERVICE.postClient(client);
        return ResponseEntity.status(201).body(createdClient);
    }

    @PreAuthorize("hasAuthority('CLIENT_UPDATE')")
    @PutMapping
    public ResponseEntity<Void> putClient(){
        // TODO Implementar funcionalidade
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PreAuthorize("hasAuthority('CLIENT_DELETE')")
    @DeleteMapping
    public ResponseEntity<Void> deleteClient(){
        // TODO Implementar funcionalidade
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
