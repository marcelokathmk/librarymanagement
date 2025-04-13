package com.company.librarymanagement.shared.exception;

import com.company.librarymanagement.book.control.exception.BookException;
import com.company.librarymanagement.fee.control.exception.FeeException;
import com.company.librarymanagement.loan.control.exception.LoanException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(BookException.class)
    public ResponseEntity<CustomError> handleBookException(BookException bookException, HttpServletRequest request) {
        final CustomError customError = new CustomError(bookException.getHttpStatus().value(), bookException.getMessage(), request.getRequestURI());
        return ResponseEntity.status(bookException.getHttpStatus()).body(customError);
    }

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<CustomError> handleLoanException(LoanException loanException, HttpServletRequest request) {
        final CustomError customError = new CustomError(loanException.getHttpStatus().value(), loanException.getMessage(), request.getRequestURI());
        return ResponseEntity.status(loanException.getHttpStatus()).body(customError);
    }

    @ExceptionHandler(FeeException.class)
    public ResponseEntity<CustomError> handleFeeException(FeeException feeException, HttpServletRequest request) {
        final CustomError customError = new CustomError(feeException.getHttpStatus().value(), feeException.getMessage(), request.getRequestURI());
        return ResponseEntity.status(feeException.getHttpStatus()).body(customError);
    }
}
