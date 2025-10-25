package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.address.Address;
import com.solarize.solarizeWebBackend.modules.address.AddressRepository;
import com.solarize.solarizeWebBackend.modules.client.dto.ClientResponseDTO;
import com.solarize.solarizeWebBackend.modules.client.dto.CreateClientDTO;
import com.solarize.solarizeWebBackend.modules.coworker.Coworker;
import com.solarize.solarizeWebBackend.modules.coworker.CoworkerRepository;
import com.solarize.solarizeWebBackend.shared.exceptions.ConflictException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository REPOSITORY;
//    private final CoworkerRepository coworkerRepository;
//    private final AddressRepository addressRepository;


    public ClientResponseDTO getClient(int id){
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
        Client client = ClientMapper.of(dto);


//        if (dto.getCoworkerLastUpdateId() != null) {
//            Coworker coworker = coworkerRepository.findById(dto.getCoworkerLastUpdateId())
//                    .orElseThrow(() -> new NotFoundException("Coworker not found."));
//            client.setCoworkerLastUpdate(coworker);
//        }
//
//        if (dto.getMainAddressId() != null) {
//            Address address = addressRepository.findById(dto.getMainAddressId())
//                    .orElseThrow(() -> new NotFoundException("Address not found."));
//            client.setMainAddress(address);
//        }

        client = REPOSITORY.save(client);
        return ClientMapper.of(client);
    }

    private void validateConflict(CreateClientDTO dto) {
        if (REPOSITORY.existsByDocumentNumber(dto.getDocumentNumber())) throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmail(dto.getEmail())) throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhone(dto.getPhone())) throw new ConflictException("Phone already exists");

        if (dto.getCnpj() != null && !dto.getCnpj().isBlank()) {
            if (REPOSITORY.existsByCnpj(dto.getCnpj())) throw new ConflictException("CNPJ already exists");
        }
    }

    public ClientResponseDTO putClient(int id, CreateClientDTO dto) {
        Optional<Client> optionalClient = REPOSITORY.findById(id);
        if (optionalClient.isEmpty()) throw new NotFoundException("Client not found.");

        Client client = optionalClient.get();
        validateConflictOnUpdate(dto, id);
        ClientMapper.updateClientData(client, dto);
        client.setUpdatedAt(java.time.LocalDateTime.now());

        client = REPOSITORY.save(client);
        return ClientMapper.of(client);
    }

    private void validateConflictOnUpdate(CreateClientDTO dto, Integer id) {
        if (REPOSITORY.existsByDocumentNumberAndIdNot(dto.getDocumentNumber(), id))
            throw new ConflictException("Document number already exists");

        if (REPOSITORY.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new ConflictException("Email already exists");

        if (REPOSITORY.existsByPhoneAndIdNot(dto.getPhone(), id))
            throw new ConflictException("Phone already exists");

        if (dto.getCnpj() != null && !dto.getCnpj().isBlank()) {
            if (REPOSITORY.existsByCnpjAndIdNot(dto.getCnpj(), id))
                throw new ConflictException("CNPJ already exists");
        }
    }

    public void deleteClient(int id) {
        Optional<Client> optionalClient = REPOSITORY.findById(id);
        if (optionalClient.isEmpty()) throw new NotFoundException("Client not found.");

        REPOSITORY.deleteById(id);
    }
}
