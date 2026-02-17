package com.solarize.solarizeWebBackend.modules.address;

import br.com.caelum.stella.format.CEPFormatter;
import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.helper.CepValidator;
import com.solarize.solarizeWebBackend.modules.address.projection.StateCityProjection;
import com.solarize.solarizeWebBackend.shared.exceptions.BadRequestException;
import com.solarize.solarizeWebBackend.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address createAddress(Address address) {
        if(!CepValidator.isValid(address.getPostalCode())) {
            throw new BadRequestException("Invalid Postal code format.");
        }

        CEPFormatter cepFormatter = new CEPFormatter();

        if(cepFormatter.isFormatted(address.getPostalCode())) {
            address.setPostalCode(cepFormatter.unformat(address.getPostalCode()));
        }

        return addressRepository.save(address);
    }

    public Address updateAddress(Address address, Long id) {
        if(address.getPostalCode() != null && !CepValidator.isValid(address.getPostalCode())) {
            throw new BadRequestException("Invalid Postal code format.");
        }

        CEPFormatter cepFormatter = new CEPFormatter();

        if(cepFormatter.isFormatted(address.getPostalCode())) {
            address.setPostalCode(cepFormatter.unformat(address.getPostalCode()));
        }

        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address does not exists"));

        existingAddress.setStreetName(
                Optional
                        .ofNullable(address.getStreetName())
                        .orElse(existingAddress.getStreetName())
        );

        existingAddress.setPostalCode(
                Optional
                        .ofNullable(address.getPostalCode())
                        .orElse(existingAddress.getPostalCode())
        );

        existingAddress.setNumber(
                Optional
                        .ofNullable(address.getNumber())
                        .orElse(existingAddress.getNumber())
        );

        existingAddress.setNeighborhood(
                Optional
                        .ofNullable(address.getNeighborhood())
                        .orElse(existingAddress.getNeighborhood())
        );

        existingAddress.setCity(
                Optional
                        .ofNullable(address.getCity())
                        .orElse(existingAddress.getCity())
        );

        existingAddress.setState(
                Optional
                        .ofNullable(address.getState())
                        .orElse(existingAddress.getState())
        );

        existingAddress.setType(
                Optional
                        .ofNullable(address.getType())
                        .orElse(existingAddress.getType())
        );

        existingAddress.setApartment(
                Optional
                        .ofNullable(address.getApartment())
                        .orElse(existingAddress.getApartment())
        );

        return addressRepository.save(existingAddress);
    }

    public Address getAddressById(Long addressId) {
        if(addressId == null) throw new BadRequestException("Address Id cannot be null.");

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address does not exists"));
    }

    public List<StateCityProjection> findAllStateCity(){
        return addressRepository.findAllStateCity();
    }
}
