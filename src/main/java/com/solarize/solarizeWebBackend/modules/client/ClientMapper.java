package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;

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
                .build();
    }
}
