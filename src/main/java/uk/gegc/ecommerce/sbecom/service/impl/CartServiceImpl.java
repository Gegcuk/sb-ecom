package uk.gegc.ecommerce.sbecom.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
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

        if (product.getQuantity() < quantity)
            throw new APIException("Only " + product.getQuantity() + " of " + product.getProductName() + " available.");

        //4. Create cart item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        //5. Save cart item
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice()) * quantity);
        cartRepository.save(cart);

        //6. return cart
        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        List<ProductDto> cartItems = cart.getCartItems()
                .stream()
                .map(item -> {
                    ProductDto map = modelMapper.map(item.getProduct(), ProductDto.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                }).toList();

        cartDto.setProducts(cartItems);

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

    @Transactional
    @Override
    public CartDto updateProductQuantityInCart(Long productId, int quantityChange) {

        String email = authUtils.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(email);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(userCart.getCartId(), productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not in cart.");
        }

        int newQuantity = cartItem.getQuantity() + quantityChange;

        if (newQuantity < 0) {
            throw new APIException("Cart quantity can't be negative.");
        }

        if (quantityChange > 0 && product.getQuantity() < quantityChange) {
            throw new APIException("Only " + product.getQuantity() + " available.");
        }

        double totalPriceAdjustment = cartItem.getProductPrice() * quantityChange;
        userCart.setTotalPrice(userCart.getTotalPrice() + totalPriceAdjustment);

        // Update cartItem and product quantity
        if (newQuantity == 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }

        product.setQuantity(product.getQuantity() - quantityChange);
        productRepository.save(product);
        cartRepository.save(userCart);

        CartDto cartDto = modelMapper.map(userCart, CartDto.class);

        List<ProductDto> productDtos = userCart.getCartItems()
                .stream()
                .map(ci -> {
                    ProductDto dto = modelMapper.map(ci.getProduct(), ProductDto.class);
                    dto.setQuantity(ci.getQuantity());
                    return dto;
                })
                .toList();

        cartDto.setProducts(productDtos);
        return cartDto;
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if(cartItem == null)
            throw new ResourceNotFoundException("Product", "productId", productId);

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

//        Product product = cartItem.getProduct();
//        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null)
            throw new APIException("Product " + product.getProductName() + " not found in the cart");

        double cartPrice = cart.getTotalPrice()-(cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepository.save(cartItem);
    }

    @PutMapping

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
