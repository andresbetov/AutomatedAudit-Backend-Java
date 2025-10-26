package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(Object... args) {
        super(ErrorCode.RESOURCE_NOT_FOUND, args);
    }
}
