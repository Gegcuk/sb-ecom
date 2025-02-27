package uk.gegc.ecommerce.sbecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be at least 5 characters")
    private String street;

    @NotBlank
    private String buildingName;

    @NotBlank
    @Size(min = 3, message = "City name must be at least 3 characters")
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must be at least 3 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Zip code must be at least 5 symbols")
    @Size(max = 10, message = "Zip code can't be longer than 10 symbols")
    private String zipcode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String zipcode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }
}
