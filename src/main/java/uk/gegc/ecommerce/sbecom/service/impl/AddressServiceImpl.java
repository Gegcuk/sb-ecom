package uk.gegc.ecommerce.sbecom.service.impl;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
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
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<AddressDto> getAllAddresses() {

        List<Address> savedAddresses = addressRepository.findAll();
        List<AddressDto> addressDtos = savedAddresses
                .stream()
                .map(address-> modelMapper.map(address, AddressDto.class))
                .toList();
        return addressDtos;
    }

    @Override
    public AddressDto getAddress(Long addressId) {
        Address address =addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return addressDto;
    }

    @Override
    public List<AddressDto> getUserAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        List<AddressDto> addressDtos = addresses
                .stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();

        return addressDtos;
    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        Address savedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        savedAddress.setStreet(addressDto.getStreet());
        savedAddress.setBuildingName(addressDto.getBuildingName());
        savedAddress.setCity(addressDto.getCity());
        savedAddress.setState(addressDto.getState());
        savedAddress.setCountry(addressDto.getCountry());
        savedAddress.setZipcode(addressDto.getZipcode());

        Address updatedAddress = addressRepository.save(savedAddress);
        AddressDto updatedAddressDto = modelMapper.map(updatedAddress, AddressDto.class);

        User user = updatedAddress.getUser();
        user.getAddresses().removeIf(userAddress -> userAddress.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return updatedAddressDto;
    }

    @Override
    public String deleteAddressById(Long addressId) {

        Address addressFromDB = addressRepository.findById(addressId)
                        .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        User user = addressFromDB.getUser();
        user.getAddresses().removeIf(a -> a.getAddressId().equals(addressFromDB.getAddressId()));
        userRepository.save(user);

        addressRepository.delete(addressFromDB);

        return "Address has been deleted with addressId = " + addressId;
    }
}
