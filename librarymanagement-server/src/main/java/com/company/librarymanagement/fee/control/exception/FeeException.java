package com.company.librarymanagement.fee.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.http.HttpStatus;

public class FeeException extends RuntimeException {

    private final HttpStatus httpStatus;

    public FeeException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage.getErrorMessage());
        this.httpStatus = libraryErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
