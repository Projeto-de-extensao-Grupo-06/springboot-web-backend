package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @PathVariable Long id
    ){
        final Client client = SERVICE.getClient(id);
        return ResponseEntity.ok(ClientMapper.of(client));
    }

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping()
    public ResponseEntity<Page<ClientResponseDTO>> getClients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ClientStatusEnum status,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ){
        final Page<Client> clients = SERVICE.getClients(search, status, pageable);
        return ResponseEntity.ok(clients.map(ClientMapper::of));
    }

    @PreAuthorize("hasAuthority('CLIENT_WRITE')")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> postClient(
            @Valid @RequestBody CreateClientDTO dto
    ){
        Address address = null;
        if (dto.getMainAddress() != null) {
            address = AddressMapper.toEntity(dto.getMainAddress());
        }

        Client client = ClientMapper.of(dto, address);
        Client created = SERVICE.postClient(client);
        return ResponseEntity.status(201).body(ClientMapper.of(created));
    }

    @PreAuthorize("hasAuthority('CLIENT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> putClient(
            @PathVariable Long id,
            @Valid @RequestBody CreateClientDTO dto
    ){
        Address address = null;
        if (dto.getMainAddress() != null) {
            address = AddressMapper.toEntity(dto.getMainAddress());
        }

        Client client = ClientMapper.of(dto, address);
        Client updated = SERVICE.putClient(id, client);
        return ResponseEntity.ok(ClientMapper.of(updated));
    }

    @PreAuthorize("hasAuthority('CLIENT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id
    ){
        SERVICE.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
