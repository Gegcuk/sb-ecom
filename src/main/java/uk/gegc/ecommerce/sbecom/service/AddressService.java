package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.User;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto, User user);

    List<AddressDto> getAllAddresses();

    AddressDto getAddress(Long addressId);

    List<AddressDto> getUserAddresses(User user);

    AddressDto updateAddress(Long addressId, AddressDto addressDto);

    String deleteAddressById(Long addressId);
}
