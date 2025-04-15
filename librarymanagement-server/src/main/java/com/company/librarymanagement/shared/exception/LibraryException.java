package com.company.librarymanagement.shared.exception;

import org.springframework.http.HttpStatus;

public class LibraryException extends RuntimeException {

    private final HttpStatus httpStatus;

    public LibraryException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage.getErrorMessage());
        this.httpStatus = libraryErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
