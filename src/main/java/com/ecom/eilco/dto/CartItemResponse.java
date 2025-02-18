package com.ecom.eilco.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private String productImage;
    private BigDecimal priceAtTime;
    private BigDecimal subTotal;
}