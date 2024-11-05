package com.nurfad.jpaexercise.common.exceptions;

import com.nurfad.jpaexercise.common.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<Response<String>> handleProductNotFoundException(DataNotFoundException e) {
        return Response.failedResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(RequestNullException.class)
    public final ResponseEntity<Response<String>> handleRequestNullException(RequestNullException e) {
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<Response<String>> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(DuplicateUniqueDataException.class)
    public final ResponseEntity<Response<String>> handleDuplicateUniqueDataException(DuplicateUniqueDataException e) {
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request. ", e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Response<String>> handleApplicationException(ApplicationException e) {
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request. ", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request. Error: " + errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<String>> handleAllExceptions(Exception e) {
        log.error(e.getMessage(), e.getCause(), e);
        if (e.getCause() instanceof UnknownHostException) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request: " + e.getLocalizedMessage());
        }
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request: " + e.getMessage());
    }
}