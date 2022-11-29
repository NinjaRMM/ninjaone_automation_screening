package com.ninja.one.degreeofseparations.model.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ApiException.class })
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        assert HttpStatus.resolve(ex.getStatusCode()) != null;
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.resolve(ex.getStatusCode()));
    }
}
