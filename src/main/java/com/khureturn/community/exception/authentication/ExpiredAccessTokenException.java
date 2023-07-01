package com.khureturn.community.exception.authentication;


import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ReturnException;
import org.springframework.http.HttpStatus;

public class ExpiredAccessTokenException extends ReturnException {

    public ExpiredAccessTokenException() {
        super(ErrCode.EXPIRED_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED);
    }

}
