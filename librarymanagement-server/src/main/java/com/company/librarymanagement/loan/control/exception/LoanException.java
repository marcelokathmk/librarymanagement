package com.company.librarymanagement.loan.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.http.HttpStatus;

public class LoanException extends RuntimeException {

    private final HttpStatus httpStatus;

    public LoanException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage.getErrorMessage());
        this.httpStatus = libraryErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
