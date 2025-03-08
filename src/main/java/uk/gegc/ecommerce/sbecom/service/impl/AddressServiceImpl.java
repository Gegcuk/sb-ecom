package uk.gegc.ecommerce.sbecom.service.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.Address;
import uk.gegc.ecommerce.sbecom.model.User;
import uk.gegc.ecommerce.sbecom.repository.AddressRepository;
import uk.gegc.ecommerce.sbecom.repository.UserRepository;
import uk.gegc.ecommerce.sbecom.service.AddressService;

import java.util.List;


@Service
@NoArgsConstructor
public class AddressServiceImpl implements AddressService {
    private ModelMapper modelMapper;
    private AddressRepository addressRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto, User user) {

        Address address = modelMapper.map(addressDto, Address.class);
        List<Address> addressList =  user.getAddresses();

        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDto.class);
    }
}
