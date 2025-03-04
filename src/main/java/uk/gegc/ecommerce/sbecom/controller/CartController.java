package uk.gegc.ecommerce.sbecom.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gegc.ecommerce.sbecom.dto.request.CartDto;
import uk.gegc.ecommerce.sbecom.service.CartItemService;
import uk.gegc.ecommerce.sbecom.service.CartSevice;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartSevice cartSevice;

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){

        CartDto cartDto = cartSevice.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

}
