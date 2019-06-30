package com.sample.paypal.application.paypal.payment.search.param.response;

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
public class PaymentHistoryContentParam implements ResponseContentsInterface {
    private List<PaymentDetail> paymentDetails;
    private int count;
    private String nextId;
}
