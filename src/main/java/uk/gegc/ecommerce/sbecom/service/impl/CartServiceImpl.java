package uk.gegc.ecommerce.sbecom.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.CartDto;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.exception.APIException;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Cart;
import uk.gegc.ecommerce.sbecom.model.CartItem;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.repository.CartItemRepository;
import uk.gegc.ecommerce.sbecom.repository.CartRepository;
import uk.gegc.ecommerce.sbecom.repository.ProductRepository;
import uk.gegc.ecommerce.sbecom.service.CartService;
import uk.gegc.ecommerce.sbecom.utils.AuthUtil;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final AuthUtil authUtils;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, AuthUtil authUtils, ProductRepository productRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.authUtils = authUtils;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDto addProductToCart(Long productId, Integer quantity) {

        //1. Find existing cart or create one
        Cart cart = createCart();

        //2. Retrieve Product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        //3. Perform validations
        if (cartItem != null) throw new APIException("Product " + product.getProductName() + " is already in the cart");

        if (product.getQuantity() == 0) throw new APIException(product.getProductName() + " is not available");

        if (product.getQuantity() < quantity) throw new APIException("There is only " + product.getQuantity() + " of "
         + product.getProductName() + " left in storage");

        //4. Create cart item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        //5. Save cart item
        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice()) * quantity);
        cartRepository.save(cart);

        //6. return cart
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDto> productStream = cartItems
                .stream()
                .map(item -> {
                    ProductDto map = modelMapper.map(item.getProduct(), ProductDto.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                });

        cartDto.setProducts(productStream.toList());

        return cartDto;
    }

    @Override
    public List<CartDto> getAllCarts() {

        List<Cart> carts = cartRepository.findAll();
        if(carts.isEmpty()) throw new APIException("No carts exists");

        List<CartDto> cartDtos = carts
                .stream()
                .map(cart ->{
                    CartDto cartDto = modelMapper.map(cart, CartDto.class);
                    cart.getCartItems()
                            .forEach(c ->
                                    c.getProduct()
                                            .setQuantity(
                                                    c.getQuantity()));
                    List<ProductDto> productDtos = cart.getCartItems()
                            .stream()
                            .map(p -> modelMapper.map(p.getProduct(), ProductDto.class)).toList();
                cartDto.setProducts(productDtos);
                return cartDto;
                })
                .toList();

        return cartDtos;
    }

    @Override
    public CartDto getCart(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email, cartId);
        if (cart == null) throw new ResourceNotFoundException("Cart", "cartId", cartId);
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cart.getCartItems()
                .forEach(c ->
                        c.getProduct()
                        .setQuantity(
                                c.getQuantity()));
        List<ProductDto> productDtos = cart.getCartItems()
                .stream()
                .map(product -> modelMapper.map(product.getProduct(), ProductDto.class))
                .toList();
        cartDto.setProducts(productDtos);
        return cartDto;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(userCart != null) return userCart;

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtils.loggedInUser());
        Cart newCart = cartRepository.save(cart);

        return newCart;
    }
}
