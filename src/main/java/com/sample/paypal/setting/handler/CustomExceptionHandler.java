package com.sample.paypal.setting.handler;

import com.sample.paypal.application.common.response.CommonResponse;
import com.sample.paypal.setting.exception.BadRequestException;
import com.sample.paypal.setting.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse badRequestHandler(BadRequestException exception) {
        log.info(exception.getMessage());
        CommonResponse responseParam = CommonResponse.builder()
                .successful(false)
                .resultCode("")
                .resultMessage(exception.getMessage())
                .contents(null)
                .build();
        return responseParam;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse businessExceptionHandler(BusinessException exception) {
        log.error(exception.getMessage());
        CommonResponse responseParam = CommonResponse.builder()
                .successful(false)
                .resultCode("")
                .resultMessage(exception.getMessage())
                .contents(null)
                .build();
        return responseParam;
    }
}
