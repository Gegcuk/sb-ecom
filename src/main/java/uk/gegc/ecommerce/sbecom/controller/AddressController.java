package uk.gegc.ecommerce.sbecom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.User;
import uk.gegc.ecommerce.sbecom.service.AddressService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

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

}
