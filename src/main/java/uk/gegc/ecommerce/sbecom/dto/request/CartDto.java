package uk.gegc.ecommerce.sbecom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ProductDto> products = new ArrayList<>();


}
