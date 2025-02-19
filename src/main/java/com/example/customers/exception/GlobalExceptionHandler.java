//TODO: ExceptionHandler will help define the error response in more detail.
//package com.example.customers.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ProblemDetail;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleGenericException(Exception ex) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + ex.getMessage());
//    }
//}

