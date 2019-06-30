package com.sample.paypal.application.paypal.payment.detail.param.response;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.sample.paypal.application.common.response.ResponseContentsInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailContentParam implements ResponseContentsInterface {
    private String paymentId;
    private String payerEmail;
    private String payerFirstName;
    private String payerLastName;
    private List<Amount> amounts;
    private String createTime;
    private String updateTime;
    private List<Item> items;
}
