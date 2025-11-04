package com.solarize.solarizeWebBackend.modules.address;

public class AddressMapper {
    public static Address toEntity(AddressDTO dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setPostalCode(dto.getPostalCode());
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setNeighborhood(dto.getNeighborhood());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setType(dto.getType());
        return address;
    }

    public static AddressDTO toDTO(Address address) {
        if (address == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setPostalCode(address.getPostalCode());
        dto.setStreetName(address.getStreetName());
        dto.setNumber(address.getNumber());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setType(address.getType());
        return dto;
    }
}
