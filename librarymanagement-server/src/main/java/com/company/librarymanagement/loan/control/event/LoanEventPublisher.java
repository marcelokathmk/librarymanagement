package com.company.librarymanagement.loan.control.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LoanEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(LoanEventPublisher.class);

    private final ApplicationEventPublisher eventPublisher;

    public LoanEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishReturnedBookEvent(final String message, final Long bookId) {
        logger.info("Publishing event for returning book {}", bookId);
        BookReturnedEvent event = new BookReturnedEvent(this, message, bookId);
        this.eventPublisher.publishEvent(event);
    }
}
