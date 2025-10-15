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

    @Operation(
            summary = "Get client by ID",
            description = "Returns a single client based on the provided ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(
            @Parameter(
                    description = "Client identifier number",
                    required = true,
                    example = "1"
            )
            @PathVariable int id
    ){
        final ClientResponseDTO client = SERVICE.getClient(id);
        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Get all clients",
            description = "Returns a list of all registered clients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of clients returned successfully",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Empty list of Clients"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getClients(){
        final List<ClientResponseDTO> clients = SERVICE.getClients();
        HttpStatus status = HttpStatus.OK;

        if(clients.isEmpty()) status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).body(clients);
    }

    @Operation(
            summary = "Create a new client",
            description = "Creates and returns a new client based on the provided data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "409", description = "Conflict of information")
    })
    @PreAuthorize("hasAuthority('CLIENT_CREATE')")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> postClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client data to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateClientDTO.class))
            )
            @Valid @RequestBody CreateClientDTO client
    ){
        final ClientResponseDTO createdClient = SERVICE.postClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @Operation(
            summary = "Create a new client",
            description = "Creates and returns a new client based on the provided data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "409", description = "Conflict of information")
    })
    @PreAuthorize("hasAuthority('CLIENT_UPDATE')")
    @PutMapping
    public ResponseEntity<Void> putClient(){
        // TODO Implementar funcionalidade
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Create a new client",
            description = "Creates and returns a new client based on the provided data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAuthority('CLIENT_DELETE')")
    @DeleteMapping
    public ResponseEntity<Void> deleteClient(){
        // TODO Implementar funcionalidade
        return ResponseEntity.ok().build();
    }

}
