package com.ecom.eilco.service;

import com.ecom.eilco.dto.OrderItemResponse;
import com.ecom.eilco.dto.OrderResponse;
import com.ecom.eilco.dto.UserUpdateRequest;
import com.ecom.eilco.model.Cart;
import com.ecom.eilco.model.Order;
import com.ecom.eilco.model.OrderItem;
import com.ecom.eilco.model.User;
import com.ecom.eilco.repository.CartRepository;
import com.ecom.eilco.repository.OrderRepository;
import com.ecom.eilco.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToOrderResponse(order);
    }

public Long createOrder(Long cartId) {
        Cart cart = cartService.getCartById(cartId);

        Order order = new Order();
        order.setUser(cart.getUser());
        
        List<OrderItem> orderItems = cart.getCartItems().stream()
        .map(cartItem -> OrderItem.builder()
            .product(cartItem.getProduct())
            .quantity(cartItem.getQuantity())
            .priceAtTime(cartItem.getPriceAtTime())
            .order(order)
            .build())
        .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setStatus("PENDING");

        orderRepository.save(order);

        cartRepository.delete(cart);

        return order.getId();
    }

    public Order updateOrder(long id, Order orderDetails) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public boolean updateOrderUser(long id, UserUpdateRequest request) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = order.getUser();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setPhone(request.getPhone());

        user = userRepository.save(user);
        return true;
    }

    private OrderResponse convertToOrderResponse(Order order) {
        List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
            .map(orderItem -> OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .productImage(orderItem.getProduct().getImageUrl())
                .priceAtTime(orderItem.getPriceAtTime())
                .subTotal(orderItem.getSubTotal())
                .build())
            .collect(Collectors.toList());

        return OrderResponse.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .orderItems(orderItemResponses)
            .totalAmount(order.getTotalAmount())
            .build();
    }
}
