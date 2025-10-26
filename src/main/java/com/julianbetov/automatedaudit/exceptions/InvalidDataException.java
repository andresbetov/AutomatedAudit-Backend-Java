package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class InvalidDataException extends BaseException {
    public InvalidDataException(Object... args) {
        super(ErrorCode.INVALID_DATA, args);
    }
}
