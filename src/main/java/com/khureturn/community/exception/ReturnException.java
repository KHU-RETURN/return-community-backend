package com.khureturn.community.exception;

import org.springframework.http.HttpStatus;

public class ReturnException extends RuntimeException {

    private final ErrCode errCode;
    private final HttpStatus status;

    public ReturnException(final ErrCode errCode, final HttpStatus status) {
        super(errCode.getMessage());
        this.errCode = errCode;
        this.status = status;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public ErrCode getErrorCode() {
        return errCode;
    }
}
