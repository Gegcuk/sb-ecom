package uk.gegc.ecommerce.sbecom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

    private Long addressId;
    private String street;
    private String buildingName;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
