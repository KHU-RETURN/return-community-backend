package com.khureturn.community.exception.ErrResponseDto;

import com.khureturn.community.exception.ErrCode;
import lombok.Data;

@Data
public class ErrResponseDto {

    private String code;

    private String message;

    public ErrResponseDto(ErrCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ErrResponseDto(String errorCode, String message){
        this.code = errorCode;
        this.message = message;
    }
}