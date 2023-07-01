package com.khureturn.community.exception.authentication;


import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ReturnException;
import org.springframework.http.HttpStatus;

public class AlreadyLoginException extends ReturnException {

    public AlreadyLoginException(){
        super(ErrCode.ALREADY_LOGIN,  HttpStatus.BAD_REQUEST);
    }
}
