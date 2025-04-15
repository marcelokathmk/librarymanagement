package com.company.librarymanagement.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<CustomError> handleBookException(LibraryException libraryException, HttpServletRequest request) {
        final CustomError customError = new CustomError(libraryException.getHttpStatus().value(), libraryException.getMessage(), request.getRequestURI());
        return ResponseEntity.status(libraryException.getHttpStatus()).body(customError);
    }
}
