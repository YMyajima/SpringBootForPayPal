package com.sample.paypal.component;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Encryptor {

    public String decrypt(String encryptedMessage, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        return encryptor.decrypt(encryptedMessage);
    }

    public String encrypt(String message, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        return encryptor.encrypt(message);
    }

}
