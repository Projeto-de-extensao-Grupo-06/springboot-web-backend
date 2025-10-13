package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class ClientMapper {

    public static ClientResponseDTO of(Client client){
        return ClientResponseDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .documentNumber(client.getDocumentNumber())
                .documentType(client.getDocumentType())
                .cnpj(client.getCnpj())
                .note(client.getNote())
                .phone(client.getPhone())
                .email(client.getEmail())
                .build();
    }

    public static List<ClientResponseDTO> of(List<Client> clients){
        List<ClientResponseDTO> clientsDTO = new ArrayList<>();

        clients.forEach(client -> {
            clientsDTO.add(
                ClientResponseDTO.builder()
                        .id(client.getId())
                        .firstName(client.getFirstName())
                        .lastName(client.getLastName())
                        .documentNumber(client.getDocumentNumber())
                        .documentType(client.getDocumentType())
                        .cnpj(client.getCnpj())
                        .note(client.getNote())
                        .phone(client.getPhone())
                        .email(client.getEmail())
                        .build()
            );
        });
        return clientsDTO;
    }
}
