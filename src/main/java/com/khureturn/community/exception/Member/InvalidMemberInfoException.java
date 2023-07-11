package com.khureturn.community.exception.Member;

import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ReturnException;
import org.springframework.http.HttpStatus;

public class InvalidMemberInfoException extends ReturnException {
    public InvalidMemberInfoException(ErrCode errCode) {
        super(errCode, HttpStatus.BAD_REQUEST);
    }
}
