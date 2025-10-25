package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientMapper {

    public static ClientResponseDTO of(Client client){
        if (client == null) return null;

        return ClientResponseDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .documentNumber(client.getDocumentNumber())
                .documentType(client.getDocumentType())
                .cnpj(client.getCnpj())
                .note(client.getNote())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .coworkerLastUpdateId(client.getCoworkerLastUpdate() != null ? client.getCoworkerLastUpdate().getId() : null)
                .mainAddressId(client.getMainAddress() != null ? client.getMainAddress().getId() : null)
                .build();
    }

    public static List<ClientResponseDTO> of(List<Client> clients){
        if (clients == null) return null;
        List<ClientResponseDTO> clientsDTO = new ArrayList<>();

        clients.forEach(client -> {
            clientsDTO.add(
                ClientResponseDTO.builder()
                        .id(client.getId())
                        .firstName(client.getFirstName())
                        .lastName(client.getLastName())
                        .phone(client.getPhone())
                        .email(client.getEmail())
                        .documentNumber(client.getDocumentNumber())
                        .documentType(client.getDocumentType())
                        .cnpj(client.getCnpj())
                        .note(client.getNote())
                        .createdAt(client.getCreatedAt())
                        .updatedAt(client.getUpdatedAt())
                        .build()
            );
        });
        return clientsDTO;
    }

    public static Client of(CreateClientDTO dto){
        if (dto == null) return null;
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .documentNumber(dto.getDocumentNumber())
                .documentType(dto.getDocumentType())
                .cnpj(dto.getCnpj())
                .note(dto.getNote())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static void updateClientData(Client client, CreateClientDTO dto) {
        if (client == null || dto == null) return;

        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());
        client.setDocumentNumber(dto.getDocumentNumber());
        client.setDocumentType(dto.getDocumentType());
        client.setCnpj(dto.getCnpj());
        client.setNote(dto.getNote());
        client.setUpdatedAt(LocalDateTime.now());
    }
}
