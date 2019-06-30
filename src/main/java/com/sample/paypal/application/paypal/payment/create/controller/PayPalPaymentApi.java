package com.sample.paypal.application.paypal.payment.create.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.application.paypal.payment.create.param.request.PayPalPaymentParam;
import com.sample.paypal.application.paypal.payment.create.param.response.PaymentContentParam;
import com.sample.paypal.domain.converter.BindingResultConverter;
import com.sample.paypal.domain.paypal.payment.service.PayPalPaymentCreateService;
import com.sample.paypal.setting.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
public class PayPalPaymentApi {

    @Autowired
    private BindingResultConverter bindingResultConverter;

    @Autowired
    private PayPalPaymentCreateService payPalPaymentCreateService;


    @RequestMapping(value = "/paypal/payments/payment", method = RequestMethod.POST)
    public CommonResponse createPayment(@Validated @RequestBody PayPalPaymentParam param,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            String message = bindingResultConverter.makeDefaultErrorMessage(bindingResult);
            throw new BadRequestException(message);
        }
        Payment payment = payPalPaymentCreateService.payment();
        List<Links> links = payment.getLinks();
        PaymentContentParam contentParam = PaymentContentParam.builder()
                .id(payment.getId())
                .build();
        for (Links link : links) {
            if ("approval_url".equalsIgnoreCase(link.getRel())) {
                log.debug("link={}", link.toString());
                contentParam.setApprovalUrl(link.getHref());
            }
        }
        CommonResponse commonResponse = CommonResponse.builder()
                .successful(true)
                .resultCode("success")
                .resultMessage("please redirect to approval_url.")
                .contents(contentParam)
                .build();
        log.debug("/paypal/payments/payment response. result={}", commonResponse.toString());
        return commonResponse;
    }
}
