package com.khureturn.community.exception.Media;

import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ReturnException;
import org.springframework.http.HttpStatus;

public class InvalidMediaType extends ReturnException {
    public InvalidMediaType() {
        super(ErrCode.INVALID_MEDIA_TYPE, HttpStatus.UNAUTHORIZED);
    }
}
