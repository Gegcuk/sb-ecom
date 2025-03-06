package uk.gegc.ecommerce.sbecom.service;

import jakarta.transaction.Transactional;
import uk.gegc.ecommerce.sbecom.dto.request.CartDto;

import java.util.List;

public interface CartService {
    CartDto addProductToCart(Long productId, Integer quantity);

    List<CartDto> getAllCarts();

    CartDto getCart(String email, Long cartId);

    @Transactional
    CartDto updateProductQuantityInCart(Long productId, int delete);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);
}
