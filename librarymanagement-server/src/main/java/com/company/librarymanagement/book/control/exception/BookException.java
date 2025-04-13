package com.company.librarymanagement.book.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.http.HttpStatus;

public class BookException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BookException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage.getErrorMessage());
        this.httpStatus = libraryErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
