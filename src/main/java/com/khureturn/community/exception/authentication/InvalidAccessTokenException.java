package com.khureturn.community.exception.authentication;


import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ReturnException;
import org.springframework.http.HttpStatus;


public class InvalidAccessTokenException extends ReturnException {

    public InvalidAccessTokenException() {
        super(ErrCode.INVALID_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED);
    }

}
