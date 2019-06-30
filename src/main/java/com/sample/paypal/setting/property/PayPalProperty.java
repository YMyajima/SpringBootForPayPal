package com.sample.paypal.setting.property;

import com.paypal.base.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "paypal")
public class PayPalProperty {
    private String mode;
    private String clientId;
    private String secret;
    private String redirectDomain;
    private String cancelUrl;
    private String returnUrl;

    public String getMode() {
        if (Constants.LIVE.equalsIgnoreCase(mode)) {
            return Constants.LIVE;
        }
        return Constants.SANDBOX;
    }

    public String makeCancelUrl() {
        return redirectDomain + cancelUrl;
    }

    public String makeReturnUrl() {
        return redirectDomain + returnUrl;
    }
}
