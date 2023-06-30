package com.khureturn.community.controller;


import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.ErrResponseDto.ErrResponseDto;
import com.khureturn.community.exception.NotValidRequestException;
import com.khureturn.community.exception.ReturnException;
import com.khureturn.community.exception.ServerInternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvisor {


    @ExceptionHandler(ReturnException.class)
    protected ResponseEntity<ErrResponseDto> handleBreakingCustomException(ReturnException e) {
        log.info(e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrResponseDto(e.getErrorCode()));
    }


    @ExceptionHandler(NotValidRequestException.class)
    protected  ResponseEntity<ErrResponseDto> handleNotValidRequestBody(NotValidRequestException e){
        log.info(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(new ErrResponseDto(e.getErrorCode(), e.getMessage()));
    }


    @ExceptionHandler(ServerInternalException.class)
    protected ResponseEntity<ErrResponseDto> handleCustomInternalErrorException(ServerInternalException e) {

        log.error("e.getMessage() = " + e.getMessage());
        log.error(e.getStackTrace().toString());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrResponseDto(ErrCode.INTERNAL_SERVER_ERROR));
    }



}

