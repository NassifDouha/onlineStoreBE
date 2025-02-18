package com.ecom.eilco.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentIntentRequest {
    private long orderId;
    private double amount;
}
