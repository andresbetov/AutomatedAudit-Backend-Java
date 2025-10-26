package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class UnexpectedErrorException extends BaseException {
    public UnexpectedErrorException(Object... args) {
        super(ErrorCode.UNEXPECTED_ERROR, args);
    }
}
