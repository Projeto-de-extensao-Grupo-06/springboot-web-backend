package com.solarize.solarizeWebBackend.modules.address;

import com.solarize.solarizeWebBackend.modules.address.dto.CreateAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.LookupResponseDTO;
import com.solarize.solarizeWebBackend.modules.address.dto.ResponseAddressDto;
import com.solarize.solarizeWebBackend.modules.address.dto.UpdateAddressDto;
import com.solarize.solarizeWebBackend.modules.address.enumerated.BrazilianState;
import com.solarize.solarizeWebBackend.modules.address.projection.StateCityProjection;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseAddressDto> createAddress(@RequestBody @Valid CreateAddressDto dto) {
        Address createdAddress = addressService.createAddress(AddressMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressMapper.toDTO(createdAddress));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<ResponseAddressDto> updateAddress(@RequestBody @Valid UpdateAddressDto dto, @PathVariable Long addressId) {
        Address createdAddress = addressService.updateAddress(AddressMapper.toEntity(dto), addressId);
        return ResponseEntity.status(HttpStatus.OK).body(AddressMapper.toDTO(createdAddress));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseAddressDto> getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(AddressMapper.toDTO(address));
    }

    @GetMapping("/lookup")
    public ResponseEntity<List<LookupResponseDTO>> getLookupData() {
        List<StateCityProjection> lookupData = addressService.findAllStateCity();
        return ResponseEntity.ok(AddressMapper.toDTO(lookupData));
    }
}
