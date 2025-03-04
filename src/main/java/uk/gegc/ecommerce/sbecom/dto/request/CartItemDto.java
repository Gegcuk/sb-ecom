package uk.gegc.ecommerce.sbecom.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long cartItemId;
    private CartDto cart;
    private ProductDto productDto;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

}
