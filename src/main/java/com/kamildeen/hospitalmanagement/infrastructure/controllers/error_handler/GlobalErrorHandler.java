package com.kamildeen.hospitalmanagement.infrastructure.controllers.error_handler;

import com.kamildeen.hospitalmanagement.infrastructure.model.response.ApiResponseJSON;
import com.kamildeen.hospitalmanagement.usecases.exceptions.BadRequestException;
import com.kamildeen.hospitalmanagement.usecases.exceptions.BusinessLogicConflictException;
import com.kamildeen.hospitalmanagement.usecases.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice(basePackages = "com.kamildeen.hospitalmanagement.infrastructure.controllers")
public class GlobalErrorHandler {

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<ApiResponseJSON<String>> handleException(Exception exception) {
        ApiResponseJSON<String> apiResponse = new ApiResponseJSON<>(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseJSON<String>> handleBadRequestException(BadRequestException e) {
        ApiResponseJSON<String> apiResponse = new ApiResponseJSON<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessLogicConflictException.class)
    public ResponseEntity<ApiResponseJSON<String>> handleBusinessLogicConflictException(BusinessLogicConflictException e) {
        ApiResponseJSON<String> apiResponse = new ApiResponseJSON<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseJSON<String>> handleNotFoundException(NotFoundException e) {
        ApiResponseJSON<String> apiResponse = new ApiResponseJSON<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


}
