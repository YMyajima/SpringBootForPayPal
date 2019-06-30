package com.sample.paypal.application.paypal.payment.detail.controller;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.application.paypal.payment.detail.param.request.PayPalPaymentDetailParam;
import com.sample.paypal.application.paypal.payment.detail.param.response.PaymentDetailContentParam;
import com.sample.paypal.domain.converter.BindingResultConverter;
import com.sample.paypal.domain.paypal.payment.service.PayPalPaymentDetailService;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class PayPalPaymentDetailApi {

    @Autowired
    private BindingResultConverter bindingResultConverter;

    @Autowired
    private PayPalPaymentDetailService payPalPaymentDetailService;

    @RequestMapping(value = "/paypal/payments/payment/detail", method = RequestMethod.POST)
    public CommonResponse paymentDetail(@Validated @RequestBody PayPalPaymentDetailParam param,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            String message = bindingResultConverter.makeDefaultErrorMessage(bindingResult);
            throw new BadRequestException(message);
        }
        Payment payment = payPalPaymentDetailService.fetchPaymentDetail(
                param.getPaymentId()
        );
        PaymentDetailContentParam paymentDetailContentParam = makeContent(payment);
        CommonResponse commonResponse = CommonResponse.builder()
                .successful(true)
                .resultCode("success")
                .resultMessage("success fetch payments data.")
                .contents(paymentDetailContentParam)
                .build();
        log.debug("/paypal/payments/payment/detail response. result={}", commonResponse.toString());
        return commonResponse;
    }

    private PaymentDetailContentParam makeContent(Payment payment) {
        PaymentDetailContentParam content = new PaymentDetailContentParam();
        content.setPaymentId(payment.getId());
        PayerInfo payerInfo = payment.getPayer().getPayerInfo();
        content.setPayerEmail(payerInfo.getEmail());
        content.setPayerFirstName(payerInfo.getFirstName());
        content.setPayerLastName(payerInfo.getLastName());
        List<Transaction> tmpTransactions = payment.getTransactions();
        List<Amount> amounts = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        for (Transaction transaction : tmpTransactions) {
            Amount amount = transaction.getAmount();
            Amount tmpAmount = new Amount();
            tmpAmount.setCurrency(amount.getCurrency());
            tmpAmount.setTotal(amount.getTotal());
            amounts.add(tmpAmount);

            List<Item> tmpItems = transaction.getItemList().getItems();
            for (Item item : tmpItems) {
                Item tmpItem = new Item();
                tmpItem.setName(item.getName());
                tmpItem.setPrice(item.getPrice());
                tmpItem.setCurrency(item.getCurrency());
                tmpItem.setTax(item.getTax());
                tmpItem.setQuantity(item.getQuantity());
                items.add(tmpItem);
            }
        }
        content.setAmounts(amounts);
        content.setItems(items);
        content.setCreateTime(payment.getCreateTime());
        content.setUpdateTime(payment.getUpdateTime());
        return content;
    }
}
