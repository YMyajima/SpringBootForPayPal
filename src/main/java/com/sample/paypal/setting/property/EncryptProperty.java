package com.sample.paypal.setting.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "encrypt")
public class EncryptProperty {
    private String databaseConnectionEncrypt;
}
