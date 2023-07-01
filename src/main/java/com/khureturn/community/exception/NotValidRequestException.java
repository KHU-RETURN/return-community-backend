package com.khureturn.community.exception;


import org.springframework.http.HttpStatus;

public class NotValidRequestException extends RuntimeException{

    private final String errorCode = "RE403";

    private final HttpStatus status = HttpStatus.BAD_REQUEST;


    public NotValidRequestException(String nullField){
        super(nullField + "가 누락되었습니다.");
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

