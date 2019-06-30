package com.sample.paypal.application.paypal.payment.result.controller;

import com.paypal.api.payments.Payment;
import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.application.paypal.payment.result.param.PayPalReturnParam;
import com.sample.paypal.domain.converter.BindingResultConverter;
import com.sample.paypal.domain.paypal.payment.service.PayPalPaymentExecuteService;
import com.sample.paypal.setting.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class PayPalReturnApi {

    @Autowired
    private BindingResultConverter bindingResultConverter;

    @Autowired
    private PayPalPaymentExecuteService payPalPaymentExecuteService;

    @RequestMapping(value = "/paypal/payment/return", method = RequestMethod.GET)
    public CommonResponse payPalPaymentReturn(@Validated PayPalReturnParam param,
                                              BindingResult bindingResult,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        // Executed when the user approves the payment.
        // Execute settlement using parameters.
        if (bindingResult.hasErrors()) {
            String message = bindingResultConverter.makeDefaultErrorMessage(bindingResult);
            throw new BadRequestException(message);
        }
        log.debug("executed payment return api. pram={}", param.toString());
        Payment payment = payPalPaymentExecuteService.executePayment(
                param.getPaymentId(),
                param.getPayerID()
        );
        return null;
    }
}
