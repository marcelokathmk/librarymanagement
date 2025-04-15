package com.company.librarymanagement.shared.exception;

import org.springframework.http.HttpStatus;

public enum LibraryErrorMessage {

    BOOK_ALREADY_LOANED(HttpStatus.CONFLICT, "The book is already loaned."),
    BOOK_NOT_LOANED(HttpStatus.NOT_FOUND, "The book is not currently loaned, so it cannot be returned."),
    BOOK_LOANED_BY_ANOTHER_USER(HttpStatus.CONFLICT, "The book is currently loaned by another user, so it cannot be returned."),

    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND,"The Book was not found in the inventory."),

    FEE_NOT_FOUND(HttpStatus.NOT_FOUND,"The fee was not found."),

    AUTH_INVALID_TOKEN(HttpStatus.BAD_REQUEST, "The token is invalid or expired."),
    AUTH_PASSWORD_INCORRECT(HttpStatus.CONFLICT, "The password is not correct."),
    AUTH_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "The user was not found for the given login.");

    private final HttpStatus httpStatus;

    private final String errorMessage;

    LibraryErrorMessage(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
