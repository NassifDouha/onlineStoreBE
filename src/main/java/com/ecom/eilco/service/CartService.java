package com.ecom.eilco.service;

import com.ecom.eilco.dto.CartItemRequest;
import com.ecom.eilco.dto.CartItemResponse;
import com.ecom.eilco.dto.CartResponse;
import com.ecom.eilco.model.Cart;
import com.ecom.eilco.model.CartItem;
import com.ecom.eilco.model.Product;
import com.ecom.eilco.model.User;
import com.ecom.eilco.repository.CartItemRepository;
import com.ecom.eilco.repository.CartRepository;
import com.ecom.eilco.repository.ProductRepository;
import com.ecom.eilco.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        
        return cart;
    }

    public CartResponse addProductToCart(Long userId, CartItemRequest cartItemRequest) {
        if (userId == 0) {
            User newUser = new User();
            userRepository.save(newUser);
            userId = newUser.getId();
        }
        
        final Long finalUserId = userId;

        Cart cart = cartRepository.findByUserId(finalUserId)
            .orElseGet(() -> {
                return createNewCart(finalUserId);
            });
        
        Product product = productRepository.findById(cartItemRequest.getProductId())
            .orElseGet(() -> {
                Product product_ = new Product();
                return product_;
            });

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
            .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
            .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + cartItemRequest.getQuantity());
            item.setPriceAtTime(product.getPrice());
            cartItemRepository.save(item);
        } else {
            CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(cartItemRequest.getQuantity())
                .priceAtTime(product.getPrice())
                .build();
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);

        return convertToCartResponse(cart);
    }

    public CartResponse updateProductQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Article du panier non trouvé"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return convertToCartResponse(cartItem.getCart());
    }

    public CartResponse removeProductFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findByProductId(cartItemId)
            .orElseThrow(() -> new RuntimeException("Article du panier non trouvé"));
        cartItemRepository.delete(cartItem);
        return convertToCartResponse(cartItem.getCart());
    }

    // get article works fine !
    public CartResponse getCartByUserId(Long userId) {
        if (userId == 0) {
            User newUser = new User();
            userRepository.save(newUser);
            userId = newUser.getId();
        }
        
        final Long finalUserId = userId;

        Cart cart = cartRepository.findByUserId(finalUserId)
            .orElseGet(() -> {
                return createNewCart(finalUserId);
            });

        return convertToCartResponse(cart);
    }

    private Cart createNewCart(Long userId) {
        Cart newCart = Cart.builder()
            .user(new User(userId))
            .cartItems(new ArrayList<>())
            .build();
        return cartRepository.save(newCart);
    }

    private CartResponse convertToCartResponse(Cart cart) {
        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
            .map(cartItem -> CartItemResponse.builder()
            .productId(cartItem.getProduct().getId())
            .productName(cartItem.getProduct().getName())
            .quantity(cartItem.getQuantity())
            .productImage(cartItem.getProduct().getImageUrl())
            .priceAtTime(cartItem.getPriceAtTime())
            .subTotal(cartItem.getSubTotal())
            .build())
            .toList();

        BigDecimal totalAmount = cartItemResponses.stream()
            .map(CartItemResponse::getSubTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
            .id(cart.getId())
            .userId(cart.getUser().getId())
            .cartItems(cartItemResponses)
            .totalAmount(totalAmount)
            .build();
    }
}