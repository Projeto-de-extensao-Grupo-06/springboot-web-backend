package com.solarize.solarizeWebBackend.modules.address;

import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.LookupResponseDTO;
import com.solarize.solarizeWebBackend.modules.address.dto.ResponseAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.UpdateAddressDto;
import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.projection.StateCityProjection;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public static List<LookupResponseDTO> toDTO(List<StateCityProjection> entities){
        if (entities == null) return null;

        Map<BrazilianState, List<String>> groupedMap = entities.stream()
                .collect(Collectors.groupingBy(
                        StateCityProjection::getState,
                        TreeMap::new,
                        Collectors.mapping(StateCityProjection::getCity, Collectors.toList())
                ));

        return groupedMap.entrySet().stream()
                .map(entry ->
                        LookupResponseDTO.builder()
                                .cities(entry.getValue())
                                .state(entry.getKey())
                                .build()
                )
                .collect(Collectors.toList());
    }

}
