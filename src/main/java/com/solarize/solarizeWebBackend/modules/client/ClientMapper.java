package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressMapper;
import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.RequestClientDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .mainAddress(AddressMapper.toDTO(client.getMainAddress()))
                .build();
    }

    public static Client of(Long id, RequestClientDto dto) {
        if (dto == null) return null;

        Client.ClientBuilder clientBuilder = Client.builder()
                .id(id)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .documentNumber(dto.getDocumentNumber())
                .documentType(dto.getDocumentType())
                .note(dto.getNote());

        if (dto.getMainAddress() != null) {
            clientBuilder.mainAddress(AddressMapper.toEntity(dto.getMainAddress()));
        }

        return clientBuilder.build();
    }

    public static List<ClientResponseDTO> of(List<Client> clients) {
        if (clients == null) return new ArrayList<>();
        return clients.stream()
                .map(ClientMapper::of)
                .collect(Collectors.toList());
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
}
