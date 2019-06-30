package com.sample.paypal.application.paypal.payment.search.controller;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentHistory;
import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.application.paypal.payment.search.param.request.PayPalSearchParam;
import com.sample.paypal.application.paypal.payment.search.param.response.PaymentDetail;
import com.sample.paypal.application.paypal.payment.search.param.response.PaymentHistoryContentParam;
import com.sample.paypal.domain.converter.BindingResultConverter;
import com.sample.paypal.domain.paypal.payment.model.PaymentHistoryRequestParam;
import com.sample.paypal.domain.paypal.payment.search.service.PayPalPaymentHistoryService;
import com.sample.paypal.setting.exception.BadRequestException;
import com.sample.paypal.utils.DateFormatUtils;
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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PayPalPaymentSearchApi {

    @Autowired
    private BindingResultConverter bindingResultConverter;

    @Autowired
    private PayPalPaymentHistoryService payPalPaymentHistoryService;

    @RequestMapping(value = "/paypal/payments/search", method = RequestMethod.POST)
    public CommonResponse paymentErrorCheck(@Validated @RequestBody PayPalSearchParam param,
                                            BindingResult bindingResult,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            String message = bindingResultConverter.makeDefaultErrorMessage(bindingResult);
            throw new BadRequestException(message);
        }
        String startTime = DateFormatUtils.toStr(
                ZonedDateTime.now(ZoneOffset.UTC),
                PaymentHistoryRequestParam.endTimeFormat
        );

        // paramから取得したパラメータを基にして実行パラメータを作成できます。
        PaymentHistoryRequestParam paymentHistoryRequestParam = PaymentHistoryRequestParam.builder()
                .sort_by("create_time")
                .sort_order("desc")
                .end_time(startTime)
                .build();
        Map<String, String> params = payPalPaymentHistoryService.makeHistoryRequestParam(paymentHistoryRequestParam);
        PaymentHistory paymentHistory = payPalPaymentHistoryService.fetchPaymentHistory(params);
        PaymentHistoryContentParam paymentHistoryContentParam = makeResponseContent(paymentHistory);
        CommonResponse commonResponse = CommonResponse.builder()
                .successful(true)
                .resultCode("success")
                .resultMessage("please redirect to approval_url.")
                .contents(paymentHistoryContentParam)
                .build();
        log.debug("/paypal/payments/search response. param={}", commonResponse.toString());
        return commonResponse;
    }

    private PaymentHistoryContentParam makeResponseContent(PaymentHistory paymentHistory) {
        // 取得したパラメータを基にして自由にレスポンスを作成できます。
        // ここでは決済の詳細を取得する際に必要なpaymentIdのみ取得しています。
        List<Payment> payments = paymentHistory.getPayments();
        List<PaymentDetail> paymentDetails = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDetail paymentDetail = PaymentDetail.builder()
                    .paymentId(payment.getId())
                    .build();
            paymentDetails.add(paymentDetail);
        }

        PaymentHistoryContentParam contentParam = PaymentHistoryContentParam.builder()
                .paymentDetails(paymentDetails)
                .count(paymentHistory.getCount())
                .nextId(paymentHistory.getNextId())
                .build();
        return contentParam;
    }
}
