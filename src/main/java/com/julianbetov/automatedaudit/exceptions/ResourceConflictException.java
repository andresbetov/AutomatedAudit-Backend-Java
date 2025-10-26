package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class ResourceConflictException extends BaseException {
    public ResourceConflictException(Object... args) {
        super(ErrorCode.RESOURCE_CONFLICT, args);
    }
}
