package com.ademkok.exception;

import com.ademkok.exception.message.ApiResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Object> buildResponseEntity(ApiResponseError error) {
        logger.error(error.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    protected ResponseEntity<Object> duplicateResourceException(DuplicateResourceException ex, WebRequest request) {

        ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }
}
