package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.User;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto, User user);
}
