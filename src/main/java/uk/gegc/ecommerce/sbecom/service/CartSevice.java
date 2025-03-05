package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.request.CartDto;

import java.util.List;

public interface CartSevice {
    CartDto addProductToCart(Long productId, Integer quantity);

    List<CartDto> getAllCarts();
}
