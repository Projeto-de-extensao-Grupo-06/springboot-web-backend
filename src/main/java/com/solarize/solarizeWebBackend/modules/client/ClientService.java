package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.client.strategy.DocumentService;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository REPOSITORY;
    private final DocumentService documentService;
    private final AddressRepository addressRepository;

    public ClientResponseDTO getClient(Long id){
        Optional<Client> client = REPOSITORY.findById(id);
        if(client.isEmpty()) throw new NotFoundException("Client not found.");
        return ClientMapper.of(client.get());
    }

    public List<ClientResponseDTO> getClients(){
        List<Client> clients = REPOSITORY.findAll();
        return ClientMapper.of(clients);
    }

    public ClientResponseDTO postClient(CreateClientDTO dto){
        validateConflict(dto);
        try {
            DocumentTypeEnum type = DocumentTypeEnum.valueOf(dto.getDocumentType().toUpperCase());
            documentService.validateDocument(type, dto.getDocumentNumber());
        } catch (IllegalArgumentException ex) {
            throw new InvalidDocumentException("Invalid document type: " + dto.getDocumentType());
        }

        Address address = null;
        if (dto.getMainAddress() != null) {
            Address newAddress = new Address();
            newAddress.setPostalCode(dto.getMainAddress().getPostalCode());
            newAddress.setStreetName(dto.getMainAddress().getStreetName());
            newAddress.setNumber(dto.getMainAddress().getNumber());
            newAddress.setNeighborhood(dto.getMainAddress().getNeighborhood());
            newAddress.setCity(dto.getMainAddress().getCity());
            newAddress.setState(dto.getMainAddress().getState());
            newAddress.setType(dto.getMainAddress().getType());

            address = addressRepository.save(newAddress);
        }

        Client client = ClientMapper.of(dto, address);

        client = REPOSITORY.save(client);
        return ClientMapper.of(client);
    }

    private void validateConflict(CreateClientDTO dto) {
        if (REPOSITORY.existsByDocumentNumber(dto.getDocumentNumber())) throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmail(dto.getEmail())) throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhone(dto.getPhone())) throw new ConflictException("Phone already exists");

    }

    public ClientResponseDTO putClient(Long id, CreateClientDTO dto) {
        Optional<Client> optionalClient = REPOSITORY.findById(id);
        if (optionalClient.isEmpty()) throw new NotFoundException("Client not found.");

        Client client = optionalClient.get();
        validateConflictOnUpdate(dto, id);

        Address address = null;
        if (dto.getMainAddress() != null) {
            Address newAddress = new Address();
            newAddress.setPostalCode(dto.getMainAddress().getPostalCode());
            newAddress.setStreetName(dto.getMainAddress().getStreetName());
            newAddress.setNumber(dto.getMainAddress().getNumber());
            newAddress.setNeighborhood(dto.getMainAddress().getNeighborhood());
            newAddress.setCity(dto.getMainAddress().getCity());
            newAddress.setState(dto.getMainAddress().getState());
            newAddress.setType(dto.getMainAddress().getType());

            address = addressRepository.save(newAddress);
        }
        ClientMapper.updateClientData(client, dto, address);
        client.setUpdatedAt(java.time.LocalDateTime.now());

        client = REPOSITORY.save(client);
        return ClientMapper.of(client);
    }

    private void validateConflictOnUpdate(CreateClientDTO dto, Long id) {
        if (REPOSITORY.existsByDocumentNumberAndIdNot(dto.getDocumentNumber(), id))
            throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhoneAndIdNot(dto.getPhone(), id))
            throw new ConflictException("Phone already exists");

    }

    public void deleteClient(Long id) {
        Optional<Client> optionalClient = REPOSITORY.findById(id);
        if (optionalClient.isEmpty()) throw new NotFoundException("Client not found.");

        REPOSITORY.deleteById(id);
    }

    
}
