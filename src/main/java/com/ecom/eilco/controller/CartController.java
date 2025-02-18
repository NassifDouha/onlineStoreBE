package com.ecom.eilco.controller;

import com.ecom.eilco.dto.CartItemRequest;
import com.ecom.eilco.dto.CartResponse;
import com.ecom.eilco.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addProductToCart(@RequestParam Long userId, @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addProductToCart(userId, cartItemRequest);
    }

    @PutMapping("/update/{cartItemId}")
    public CartResponse updateProductQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        return cartService.updateProductQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public CartResponse removeProductFromCart(@PathVariable Long cartItemId) {
        return cartService.removeProductFromCart(cartItemId);
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }
}