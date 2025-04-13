package uk.gegc.ecommerce.sbecom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.gegc.ecommerce.sbecom.dto.request.AddressDto;
import uk.gegc.ecommerce.sbecom.model.User;
import uk.gegc.ecommerce.sbecom.repository.AddressRepository;
import uk.gegc.ecommerce.sbecom.security.jwt.AuthTokenFilter;
import uk.gegc.ecommerce.sbecom.security.jwt.JwtUtils;
import uk.gegc.ecommerce.sbecom.service.AddressService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AddressController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuthTokenFilter.class))
@AutoConfigureMockMvc(addFilters = false)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private AuthUtil authUtil;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    public void testCreateAddressReturnsCreatedStatus() throws Exception{
        AddressDto inputDto = new AddressDto();
        inputDto.setBuildingName("1");
        inputDto.setStreet("Kew Foot");
        inputDto.setCity("SPb");
        inputDto.setZipcode("TW10TW9");
        inputDto.setCountry("UK");

        User user = new User("John Doe", "luke@skywalker.txt", "skywalkerPassword");

        AddressDto savedDto = new AddressDto();
        savedDto.setBuildingName("1");
        savedDto.setStreet("Kew Foot");
        savedDto.setCity("SPb");
        savedDto.setZipcode("TW10TW9");
        savedDto.setCountry("UK");

        when(authUtil.loggedInUser()).thenReturn(user);
        when(addressService.createAddress(any(AddressDto.class), eq(user))).thenReturn(savedDto);

        mockMvc.perform(post("/api/address")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"buildingName\":\"1\", \"street\":\"Kew Foot\", \"city\":\"SPb\", \"zipcode\":\"TW10TW9\", \"country\":\"UK\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value("Kew Foot"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void testGetAllAddressesReturnsOkStatusAndAddresses() throws Exception{
        List<AddressDto> addressDtoList = new ArrayList<>();
        AddressDto inputDto = new AddressDto();
        inputDto.setBuildingName("1");
        inputDto.setStreet("Kew Foot");
        inputDto.setCity("SPb");
        inputDto.setZipcode("TW10TW9");
        inputDto.setCountry("UK");
        addressDtoList.add(inputDto);

        when(addressService.getAllAddresses()).thenReturn(addressDtoList);

        mockMvc.perform(get("/api/addresses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].street").value("Kew Foot"))
                .andExpect(jsonPath("$[0].city").value("SPb"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void testGetAddressByIdReturnsOk() throws Exception{

        Long addressId = 1L;
        AddressDto inputDto = new AddressDto();
        inputDto.setBuildingName("1");
        inputDto.setStreet("Kew Foot");
        inputDto.setCity("SPb");
        inputDto.setZipcode("TW10TW9");
        inputDto.setCountry("UK");

        when(addressService.getAddress(addressId)).thenReturn(inputDto);

        mockMvc.perform(get("/api/address/{addressId}", addressId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Kew Foot"))
                .andExpect(jsonPath("$.city").value("SPb"));


    }

}
