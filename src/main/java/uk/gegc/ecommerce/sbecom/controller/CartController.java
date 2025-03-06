package uk.gegc.ecommerce.sbecom.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.ecommerce.sbecom.dto.request.CartDto;
import uk.gegc.ecommerce.sbecom.model.Cart;
import uk.gegc.ecommerce.sbecom.repository.CartRepository;
import uk.gegc.ecommerce.sbecom.service.CartService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartSevice;
    private final AuthUtil authUtil;
    private final CartRepository cartRepository;

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){

        CartDto cartDto = cartSevice.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> getCarts(){
        List<CartDto> cartDtos = cartSevice.getAllCarts();
        return new ResponseEntity<>(cartDtos, HttpStatus.FOUND);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartDto> getCartByUserId(){
        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        CartDto cartDto = cartSevice.getCart(email, cart.getCartId());
        return new ResponseEntity<>(cartDto, HttpStatus.FOUND);
    }

    @PutMapping("/product/{productId}/quantity/{operation}")
    public ResponseEntity<CartDto> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation){
        CartDto cartDto = cartSevice.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        String status = cartSevice.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
