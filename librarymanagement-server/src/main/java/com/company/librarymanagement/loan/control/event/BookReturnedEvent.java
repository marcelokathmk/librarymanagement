package com.company.librarymanagement.loan.control.event;

import org.springframework.context.ApplicationEvent;

public class BookReturnedEvent extends ApplicationEvent {

    private final String message;

    private final Long bookId;

    public BookReturnedEvent(Object source, String message, Long bookId) {
        super(source);
        this.message = message;
        this.bookId = bookId;
    }

    public String getMessage() {
        return message;
    }

    public Long getBookId() {
        return bookId;
    }
}
