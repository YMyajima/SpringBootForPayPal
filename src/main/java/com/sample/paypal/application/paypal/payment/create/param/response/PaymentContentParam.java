package com.sample.paypal.application.paypal.payment.create.param.response;

import com.sample.paypal.application.common.response.ResponseContentsInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContentParam implements ResponseContentsInterface {
    private String approvalUrl;
    private String id;
}
