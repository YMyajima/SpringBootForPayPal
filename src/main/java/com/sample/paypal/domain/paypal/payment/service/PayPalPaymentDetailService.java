package com.sample.paypal.domain.paypal.payment.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sample.paypal.setting.exception.BusinessException;
import com.sample.paypal.setting.property.PayPalProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayPalPaymentDetailService {

    @Autowired
    private PayPalProperty payPalProperty;

    public Payment fetchPaymentDetail(String paymentId) {
        APIContext apiContext = new APIContext(
                payPalProperty.getClientId(),
                payPalProperty.getSecret(),
                payPalProperty.getMode()
        );
        try {
            Payment payment = Payment.get(apiContext, paymentId);
            log.debug(payment.toJSON());
            return payment;
        } catch (PayPalRESTException e) {
            log.error("failed fetch payment detail. error={}", e.getDetails().getMessage());
            throw new BusinessException("failed fetch payment detail.");
        }
    }
}
