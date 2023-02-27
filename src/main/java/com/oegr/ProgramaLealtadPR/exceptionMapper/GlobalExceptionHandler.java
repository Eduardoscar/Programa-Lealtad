package com.oegr.ProgramaLealtadPR.exceptionMapper;

import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundExceptionMapper.class)
    public ResponseEntity<ErrorDetails> NotFoundException (NotFoundExceptionMapper exception) {
        String msg = exception.getMessage();
        HttpStatus code = HttpStatus.NOT_FOUND;
        ErrorDetails errorModel = new ErrorDetails(code.value(), msg);
        log.warn("status: {} message: {}", code.value(), msg);
        return new ResponseEntity<ErrorDetails>(errorModel, code);
    }

    @ExceptionHandler(BadRequestExceptionMapper.class)
    public ResponseEntity<ErrorDetails> BadRequestExceptionMapper(BadRequestExceptionMapper exception) {
        String msg = exception.getMessage();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        ErrorDetails errorModel = new ErrorDetails(code.value(), msg);
        log.warn("status: {} message: {}", code.value(), msg);
        return new ResponseEntity<ErrorDetails>(errorModel, code);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception exception) {
        String msg = exception.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDetails errorModel = new ErrorDetails(status.value(), msg);
        log.error("status: {} message: {}", status.value(), msg);
        return new ResponseEntity<>(errorModel, status);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> ConstraintViolationException (jakarta.validation.ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        HttpStatus code = HttpStatus.BAD_REQUEST;
        ErrorDetails errorModel = new ErrorDetails(code.value(), msg);
        log.warn("status: {} message: {}", code.value(), msg);
        return new ResponseEntity<ErrorDetails>(errorModel, code);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> MethodNotAllowedExceptionMapper(HttpRequestMethodNotSupportedException exception) {
        String msg = exception.getMessage();
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorDetails errorModel = new ErrorDetails(status.value(), msg);
        log.error("status: {} message: {}", status.value(), msg);
        return new ResponseEntity<>(errorModel, status);
    }
}
