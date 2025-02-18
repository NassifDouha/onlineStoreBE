package com.ecom.eilco.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class PaymentConfirmationRequest {
    private String paymentIntentId;
}
