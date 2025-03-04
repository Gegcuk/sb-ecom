package uk.gegc.ecommerce.sbecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gegc.ecommerce.sbecom.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("Select ci from CartItem ci where ci.cart.id = ? AND ci.product.id = ?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
}
