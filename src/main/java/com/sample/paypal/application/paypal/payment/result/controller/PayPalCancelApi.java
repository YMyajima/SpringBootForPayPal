package com.sample.paypal.application.paypal.payment.result.controller;

import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.application.paypal.payment.result.param.PayPalCancelParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class PayPalCancelApi {


    @RequestMapping(value = "/paypal/payment/cancel", method = RequestMethod.GET)
    public CommonResponse payPalPaymentCancel(@Validated PayPalCancelParam param,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              BindingResult bindingResult) {
        // executed when the settlement is canceled.
        log.debug("executed payment cancel api. param={}", param.toString());
        return null;
    }
}
