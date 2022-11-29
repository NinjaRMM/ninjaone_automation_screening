package com.ninja.one.degreeofseparations.model.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiException extends Exception {
    private String message;
    private Integer statusCode;
}
