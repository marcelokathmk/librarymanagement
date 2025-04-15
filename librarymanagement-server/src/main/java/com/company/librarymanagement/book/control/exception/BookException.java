package com.company.librarymanagement.book.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import com.company.librarymanagement.shared.exception.LibraryException;
import org.springframework.http.HttpStatus;

public class BookException extends LibraryException {

    public BookException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage);
    }

}
