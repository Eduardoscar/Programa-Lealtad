package com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExceptionMapper extends RuntimeException{
    public BadRequestExceptionMapper(String message){
        super(message);
    }
}
