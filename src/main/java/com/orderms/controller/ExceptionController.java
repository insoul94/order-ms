package com.orderms.controller;

import com.orderms.exception.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    protected ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, String msg) {
        log.error(
                """

                        ----------------------------------------------------------
                        \tStatus: \t\t\t{}
                        \tException Class: \t{}
                        \tMessage: \t\t\t{}
                        ----------------------------------------------------------
                        \t""",
                status,
                ex.getClass(),
                msg);
        return new ResponseEntity<>(msg, status);
    }
}