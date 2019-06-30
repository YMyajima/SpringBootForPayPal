package com.sample.paypal.domain.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BindingResultConverter {

    /**
     * エラー時のメッセージを作成します。
     * 以下のようなメッセージが作成されます。
     * 適宜MessageSourceを利用してメッセージの国際化対応が可能です。
     * paymentId=[must not be blank]
     *
     * @param bindingResult
     * @return
     */
    public String makeDefaultErrorMessage(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            String appendMessage = fieldName + "=[" + defaultMessage + "]";
            errorMessages.add(appendMessage);
        }
        String resultMessage = String.join(".", errorMessages);
        return resultMessage;
    }
}
