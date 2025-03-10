package uk.gegc.ecommerce.sbecom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long orderItemId;
    private ProductDto product;
    private Integer quantity;
    private Double discount;
    private Double orderedProductPrice;
}
