package com.sample.paypal.application.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommonResponse {

    @JsonProperty("isSuccessful")
    private boolean successful;
    private String resultCode;
    private String resultMessage;
    private ResponseContentsInterface contents;
}
