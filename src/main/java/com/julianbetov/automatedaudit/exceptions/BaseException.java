package com.julianbetov.automatedaudit.exceptions;


import com.julianbetov.automatedaudit.utils.ApplicationContextHolder;
import com.julianbetov.automatedaudit.utils.ErrorCode;
import com.julianbetov.automatedaudit.utils.MessageService;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int code;
    private final String value;
    private final String message;

    public BaseException(ErrorCode errorCode, Object... args) {
        this.code = errorCode.getCode();
        this.value = errorCode.getValue();
        this.message = getLocalizedMessage(errorCode, args);
    }

    private String getLocalizedMessage(ErrorCode errorCode, Object... args) {
        try {
            MessageService messageService = ApplicationContextHolder.getBean(MessageService.class);
            return messageService.getMessage(errorCode, args);
        } catch (Exception e) {
            return errorCode.getValue();
        }
    }

}