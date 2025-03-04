package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.request.CartDto;

public interface CartSevice {
    CartDto addProductToCart(Long productId, Integer quantity);
}
