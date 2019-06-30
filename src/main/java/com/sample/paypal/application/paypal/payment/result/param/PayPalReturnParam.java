package com.sample.paypal.application.paypal.payment.result.param;

import lombok.Data;

@Data
public class PayPalReturnParam {
    private String paymentId;
    private String token;
    private String PayerID;
}
