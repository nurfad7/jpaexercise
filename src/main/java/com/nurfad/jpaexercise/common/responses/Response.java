package com.nurfad.jpaexercise.common.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Response<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public Response(int statusCode, String statusDesc) {
        this.statusCode = statusCode;
        message = statusDesc;
        success = statusCode - 200 < 100;
        timestamp = LocalDateTime.now();
    }

    public static <T> ResponseEntity<Response<T>> successfulResponse(String message, T data) {
        return successfulResponse(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResponseEntity<Response<T>> successfulResponse(String message) {
        return successfulResponse(HttpStatus.OK.value(), message, null);
    }

    public static <T> ResponseEntity<Response<T>> successfulResponse(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(statusCode).body(response);
    }

    public static <T> ResponseEntity<Response<Object>> failedResponse(String message) {
        return failedResponse(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> ResponseEntity<Response<T>> failedResponse(T data) {
        return failedResponse(HttpStatus.BAD_REQUEST.value(), "Bad request", data);
    }

    public static <T> ResponseEntity<Response<T>> failedResponse(int statusCode, String message) {
        return failedResponse(statusCode, message, null);
    }

    public static <T> ResponseEntity<Response<T>> failedResponse(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(false);
        response.setData(data);
        return ResponseEntity.status(statusCode).body(response);
    }
}
