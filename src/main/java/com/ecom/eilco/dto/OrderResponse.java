package com.ecom.eilco.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private List<OrderItemResponse> orderItems;
    private BigDecimal totalAmount;
}