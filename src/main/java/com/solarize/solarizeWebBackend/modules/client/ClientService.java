package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
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

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public Client getClient(Long id) {
        return clientRepository.findById(id)
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

        return clientRepository.findAllClients(search, status, city, state, startDateTime, endDateTime, pageable);
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

        return clientRepository.save(client);
    }

    @Transactional
    public Client putClient(Client updatedClient) {
        Client existingClient = clientRepository.findById(updatedClient.getId())
                .orElseThrow(() -> new NotFoundException("Client not found."));

        this.validateConflictOnUpdate(updatedClient);

        try {
            DocumentTypeEnum type = DocumentTypeEnum.valueOf(updatedClient.getDocumentType().toUpperCase());
            type.strategy.validate(updatedClient.getDocumentNumber());
        } catch (IllegalArgumentException ex) {
            throw new InvalidDocumentException("Invalid document type: " + updatedClient.getDocumentType());
        }

        if (updatedClient.getMainAddress() != null) {
            if (existingClient.getMainAddress() != null) {
                updateAddressFields(existingClient.getMainAddress(), updatedClient.getMainAddress());
            } else {
                existingClient.setMainAddress(updatedClient.getMainAddress());
            }
        }

        existingClient.setFirstName(updatedClient.getFirstName());
        existingClient.setLastName(updatedClient.getLastName());
        existingClient.setDocumentNumber(updatedClient.getDocumentNumber());
        existingClient.setDocumentType(updatedClient.getDocumentType());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setNote(updatedClient.getNote());
        existingClient.setUpdatedAt(LocalDateTime.now());

        return clientRepository.save(existingClient);
    }

    private void updateAddressFields(Address target, Address source) {
        target.setPostalCode(source.getPostalCode());
        target.setStreetName(source.getStreetName());
        target.setNumber(source.getNumber());
        target.setNeighborhood(source.getNeighborhood());
        target.setCity(source.getCity());
        target.setState(source.getState());
        target.setApartment(source.getApartment());
        target.setType(source.getType());
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found."));

        client.setStatus(ClientStatusEnum.INACTIVE);
        clientRepository.save(client);
    }

    private void validateConflict(Client client) {
        if (clientRepository.existsByDocumentNumber(client.getDocumentNumber()))
            throw new ConflictException("Document number already exists");

        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ConflictException("Email already exists");

        if (clientRepository.existsByPhone(client.getPhone()))
            throw new ConflictException("Phone already exists");
    }

    private void validateConflictOnUpdate(Client client) {
        if (clientRepository.existsByDocumentNumberAndIdNot(client.getDocumentNumber(), client.getId()))
            throw new ConflictException("Document number already exists");

        if (clientRepository.existsByEmailAndIdNot(client.getEmail(), client.getId()))
            throw new ConflictException("Email already exists");

        if (clientRepository.existsByPhoneAndIdNot(client.getPhone(), client.getId()))
            throw new ConflictException("Phone already exists");
    }

}
