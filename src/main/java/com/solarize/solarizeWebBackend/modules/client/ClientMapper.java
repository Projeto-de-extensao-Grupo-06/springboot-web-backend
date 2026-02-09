package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.RequestClientDto;

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
                .note(client.getNote())
                .createdAt(client.getCreatedAt())
                .mainAddress(of(client.getMainAddress()))
                .build();
    }

    public static Client of(Long id, RequestClientDto client){
        if (client == null) return null;

        return Client.builder()
                .id(id)
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .documentNumber(client.getDocumentNumber())
                .documentType(client.getDocumentType())
                .note(client.getNote())
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
                        .note(client.getNote())
                        .createdAt(client.getCreatedAt())
                        .mainAddress(of(client.getMainAddress()))
                        .build()
            );
        });
        return clientsDTO;
    }

    public static Client of(CreateClientDTO dto, Address address){
        if (dto == null) return null;
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .documentNumber(dto.getDocumentNumber())
                .documentType(dto.getDocumentType())
                .note(dto.getNote())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .mainAddress(address)
                .build();
    }

    public static void updateClientData(Client client, CreateClientDTO dto, Address address) {
        if (client == null || dto == null) return;

        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());
        client.setDocumentNumber(dto.getDocumentNumber());
        client.setDocumentType(dto.getDocumentType());
        client.setNote(dto.getNote());
        client.setMainAddress(address);
        client.setUpdatedAt(LocalDateTime.now());
    }

    private static CreateAddressDto of(Address address){
        if (address == null) return null;

        return CreateAddressDto.builder()
                .postalCode(address.getPostalCode())
                .streetName(address.getStreetName())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .type(address.getType())
                .build();
    }
}
