package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ApiResponse<Map<String,String>> res = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),false,"Something went error",errors);
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(error ->
                errors.put(error.getPropertyPath().toString(), error.getMessage())
        );

        ApiResponse<Map<String, String>> res =
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), false, "Validation Failed", errors);

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(
            RouteAssignedToBusException.class
    )
    public ResponseEntity<ApiResponse<?>>
    handleRouteAssignedException(
            RouteAssignedToBusException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                400,
                                false,
                                "Route assigned to some buses",
                                null
                        )
                );
    }

    @ExceptionHandler(
            InsufficientWalletException.class
    )
    public ResponseEntity<ApiResponse<?>>
    handleInsufficientWalletException(
            InsufficientWalletException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                400,
                                false,
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(
            InvalidCredentialsException.class
    )
    public ResponseEntity<ApiResponse<?>>
    handleInvalidCredentials(
            InvalidCredentialsException e
    ) {

        return ResponseEntity
                .status(401)
                .body(

                        new ApiResponse<>(

                                401,

                                false,

                                e.getMessage(),

                                null
                        )
                );
    }
}
