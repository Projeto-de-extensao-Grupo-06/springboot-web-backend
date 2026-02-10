package com.solarize.solarizeWebBackend.modules.address;

import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.ResponseAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.UpdateAddressDto;

public class AddressMapper {
    public static Address toEntity(CreateAddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setPostalCode(dto.getPostalCode());
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setType(dto.getType());
        address.setApartment(dto.getApartment());

        return address;
    }


    public static Address toEntity(UpdateAddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setPostalCode(dto.getPostalCode());
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setType(dto.getType());
        address.setApartment(dto.getApartment());

        return address;
    }

    public static ResponseAddressDto toDTO(Address address) {
        if (address == null) return null;

        return ResponseAddressDto.builder()
                .id(address.getId())
                .streetName(address.getStreetName())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .stateName(address.getState().getFullName())
                .type(address.getType())
                .apartment(address.getApartment())
                .build();
    }
}
