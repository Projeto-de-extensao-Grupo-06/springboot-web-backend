package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository REPOSITORY;
    private final AddressRepository addressRepository;

    public Client getClient(Long id) {
        return REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found."));
    }

    public Page<Client> getClients(
            String search,
            ClientStatusEnum status,
            String city,
            String state,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return REPOSITORY.findAllClients(search, status, city, state, startDateTime, endDateTime, pageable);
    }

    public Client postClient(Client client) {
        validateConflict(client);

        try {
            DocumentTypeEnum type = DocumentTypeEnum.valueOf(client.getDocumentType().toUpperCase());
            type.strategy.validate(client.getDocumentNumber());
        } catch (IllegalArgumentException ex) {
            throw new InvalidDocumentException("Invalid document type: " + client.getDocumentType());
        }


        if (client.getMainAddress() != null) {
            Address savedAddress = addressRepository.save(client.getMainAddress());
            client.setMainAddress(savedAddress);
        }

        return REPOSITORY.save(client);
    }

    public Client putClient(Long id, Client updatedClient) {
        Client existingClient = REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found."));

        validateConflictOnUpdate(updatedClient, id);


        try {
            DocumentTypeEnum type = DocumentTypeEnum.valueOf(updatedClient.getDocumentType().toUpperCase());
            type.strategy.validate(updatedClient.getDocumentNumber());
        } catch (IllegalArgumentException ex) {
            throw new InvalidDocumentException("Invalid document type: " + updatedClient.getDocumentType());
        }


        if (updatedClient.getMainAddress() != null) {
            Address savedAddress = addressRepository.save(updatedClient.getMainAddress());
            existingClient.setMainAddress(savedAddress);
        }
        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setLastName(updatedClient.getLastName());
        existingClient.setDocumentNumber(updatedClient.getDocumentNumber());
        existingClient.setDocumentType(updatedClient.getDocumentType());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setUpdatedAt(LocalDateTime.now());

        return REPOSITORY.save(existingClient);
    }

    public void deleteClient(Long id) {
        Client client = REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found."));

        client.setStatus(ClientStatusEnum.INACTIVE);
        REPOSITORY.save(client);
    }

    private void validateConflict(Client client) {
        if (REPOSITORY.existsByDocumentNumber(client.getDocumentNumber()))
            throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmail(client.getEmail()))
            throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhone(client.getPhone()))
            throw new ConflictException("Phone already exists");
    }

    private void validateConflictOnUpdate(Client client, Long id) {
        if (REPOSITORY.existsByDocumentNumberAndIdNot(client.getDocumentNumber(), id))
            throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmailAndIdNot(client.getEmail(), id))
            throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhoneAndIdNot(client.getPhone(), id))
            throw new ConflictException("Phone already exists");
    }

}
