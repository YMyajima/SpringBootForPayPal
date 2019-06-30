package com.sample.paypal.application.paypal.payment.detail.param.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PayPalPaymentDetailParam {
    @NotBlank
    private String paymentId;
}
