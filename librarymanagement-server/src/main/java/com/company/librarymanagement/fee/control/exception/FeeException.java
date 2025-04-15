package com.company.librarymanagement.fee.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import com.company.librarymanagement.shared.exception.LibraryException;

public class FeeException extends LibraryException {

    public FeeException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage);
    }

}
