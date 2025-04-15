package com.company.librarymanagement.auth.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import com.company.librarymanagement.shared.exception.LibraryException;

public class AuthException extends LibraryException {

    public AuthException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage);
    }
}
