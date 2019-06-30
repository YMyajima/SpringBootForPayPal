package com.sample.paypal.application.paypal.payment.search.param.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetail {
    private String paymentId;
}
