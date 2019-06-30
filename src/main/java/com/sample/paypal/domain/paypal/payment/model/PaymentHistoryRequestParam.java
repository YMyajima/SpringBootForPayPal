package com.sample.paypal.domain.paypal.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryRequestParam {
    @Builder.Default
    private String count = "20";  // max 20.
    private String start_id;
    @Builder.Default
    private String start_index = "0";
    private String start_time;  // utc time. yyyy-mm-ddThh:mm:ssZ
    private String end_time;  // utc time. yyyy-mm-ddThh:mm:ssZ
    private String payee_id;  // account merchant id or null
    private String sort_by;  // create_time or null
    private String sort_order;  // desc or null

    public static final String startTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String endTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
}
