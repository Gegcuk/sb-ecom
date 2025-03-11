package uk.gegc.ecommerce.sbecom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.ecommerce.sbecom.dto.request.OrderDto;
import uk.gegc.ecommerce.sbecom.dto.request.OrderRequestDto;
import uk.gegc.ecommerce.sbecom.service.OrderService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDto> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDto orderRequestDto){

        String emailId = authUtil.loggedInEmail();
        OrderDto order = orderService.placeOrder(
                emailId,
                orderRequestDto.getAddressId(),
                paymentMethod,
                orderRequestDto.getPgName(),
                orderRequestDto.getPgPaymentId(),
                orderRequestDto.getPgStatus(),
                orderRequestDto.getPgResponseMessage()
        );

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
