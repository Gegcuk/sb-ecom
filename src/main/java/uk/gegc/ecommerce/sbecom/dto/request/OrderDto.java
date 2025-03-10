package uk.gegc.ecommerce.sbecom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private String email;
    private List<OrderItemDto> orderItems;
    private LocalDate orderDate;
    private PaymentDto payment;
    private Double totalAmount;
    private String orderStatus;
    private Long addressId;

}
