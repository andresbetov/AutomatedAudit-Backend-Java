package com.julianbetov.automatedaudit.exceptions;

import com.julianbetov.automatedaudit.utils.ErrorCode;

public class DatabaseException extends BaseException {
    public DatabaseException(Object... args) {
        super(ErrorCode.DATABASE_ERROR, args);
    }
}
