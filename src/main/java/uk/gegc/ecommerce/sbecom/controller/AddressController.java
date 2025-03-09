package uk.gegc.ecommerce.sbecom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.User;
import uk.gegc.ecommerce.sbecom.service.AddressService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @PostMapping("/address")
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto addressDto){
        User user = authUtil.loggedInUser();
        AddressDto savedAddress = addressService.createAddress(addressDto, user);

        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        List<AddressDto> addressDtos = addressService.getAllAddresses();

        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Long addressId){
        AddressDto addressDto = addressService.getAddress(addressId);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @GetMapping("/address/user")
    public ResponseEntity<List<AddressDto>> getAddressesByUser(){
        User user = authUtil.loggedInUser();
        List<AddressDto> addressDtos = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> updateAddressIdById(@PathVariable Long addressId,
                                                @RequestBody AddressDto addressDto){
        AddressDto updatedAddressDto = addressService.updateAddress(addressId, addressDto);

        return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId){
        String status = addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
