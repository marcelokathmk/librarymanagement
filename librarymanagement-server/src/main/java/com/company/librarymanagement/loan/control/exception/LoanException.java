package com.company.librarymanagement.loan.control.exception;

import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import com.company.librarymanagement.shared.exception.LibraryException;

public class LoanException extends LibraryException {

    public LoanException(LibraryErrorMessage libraryErrorMessage) {
        super(libraryErrorMessage);
    }
}
