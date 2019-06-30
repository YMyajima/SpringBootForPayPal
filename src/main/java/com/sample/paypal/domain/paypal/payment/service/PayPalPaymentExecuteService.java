package com.sample.paypal.domain.paypal.payment.service;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sample.paypal.setting.exception.BusinessException;
import com.sample.paypal.setting.property.PayPalProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayPalPaymentExecuteService {

    @Autowired
    private PayPalProperty payPalProperty;

    public Payment executePayment(String paymentId, String payerId) {
        APIContext apiContext = new APIContext(
                payPalProperty.getClientId(),
                payPalProperty.getSecret(),
                payPalProperty.getMode()
        );
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            log.debug("executed payment result. result={}", createdPayment.toJSON());
            return createdPayment;
        } catch (PayPalRESTException e) {
            log.error("failed execute payment. error={}", e.getDetails());
            throw new BusinessException("failed execute payment. " + e.getDetails().getMessage());
        }
    }
}
