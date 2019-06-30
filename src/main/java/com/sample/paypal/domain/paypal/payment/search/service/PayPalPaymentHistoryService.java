package com.sample.paypal.domain.paypal.payment.search.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentHistory;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sample.paypal.domain.paypal.payment.model.PaymentHistoryRequestParam;
import com.sample.paypal.setting.exception.BusinessException;
import com.sample.paypal.setting.property.PayPalProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class PayPalPaymentHistoryService {

    @Autowired
    private PayPalProperty payPalProperty;

    @Autowired
    private ObjectMapper objectMapper;

    public PaymentHistory fetchPaymentHistory(Map<String, String> searchParams) {
        try {
            APIContext apiContext = new APIContext(
                    payPalProperty.getClientId(),
                    payPalProperty.getSecret(),
                    payPalProperty.getMode()
            );
            PaymentHistory paymentHistory = Payment.list(apiContext, searchParams);
            log.info("success get payment list. result={}", paymentHistory.toString());
            return paymentHistory;
        } catch (PayPalRESTException e) {
            log.error("failed get payment list.", e);
            throw new BusinessException("failed get payment list. " + e.getDetails().getMessage());
        }
    }

    public Map<String, String> makeHistoryRequestParam(PaymentHistoryRequestParam param) {
        // map.putを使用するよりobjectからmapを作ったほうが今後リファクタリングするやりやすくなります。
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<String, String> map = objectMapper.convertValue(param, Map.class);
        log.debug("object was converted to map. result={}", map.toString());
        return map;
    }
}
