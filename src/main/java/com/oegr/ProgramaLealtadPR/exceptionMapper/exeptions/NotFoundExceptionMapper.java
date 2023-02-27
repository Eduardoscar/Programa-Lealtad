package com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptionMapper extends RuntimeException {

    public NotFoundExceptionMapper(String message) {
        super(message);
    }

}