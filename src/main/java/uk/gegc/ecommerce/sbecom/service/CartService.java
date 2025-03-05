package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.request.CartDto;

import java.util.List;

public interface CartService {
    CartDto addProductToCart(Long productId, Integer quantity);

    List<CartDto> getAllCarts();

    CartDto getCart(String email, Long cartId);
}
