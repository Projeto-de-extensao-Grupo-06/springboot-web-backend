package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressMapper;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.RequestClientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(
            @PathVariable Long id
    ){
        final Client client = clientService.getClient(id);
        return ResponseEntity.ok(ClientMapper.of(client));
    }

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping()
    public ResponseEntity<Page<ClientResponseDTO>> getClients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ClientStatusEnum status,
            @RequestParam(required = false) List<String> city,
            @RequestParam(required = false) List<String> state,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(page = 0, size = 30) Pageable pageable
    ){
        final Page<Client> clients = clientService.getClients(search, status, city, state, startDate, endDate, pageable);
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
        Client created = clientService.postClient(client);
        return ResponseEntity.status(201).body(ClientMapper.of(created));
    }

    @PreAuthorize("hasAuthority('CLIENT_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> putClient(
            @PathVariable Long id,
            @Valid @RequestBody RequestClientDto dto
    ){
        Client client = ClientMapper.of(id, dto);
        Client updated = clientService.putClient(client);
        return ResponseEntity.ok(ClientMapper.of(updated));
    }

    @PreAuthorize("hasAuthority('CLIENT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id
    ){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
