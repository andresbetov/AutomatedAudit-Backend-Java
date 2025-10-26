package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(Object... args) {
        super(ErrorCode.SERVICE_UNAVAILABLE, args);
    }
}
