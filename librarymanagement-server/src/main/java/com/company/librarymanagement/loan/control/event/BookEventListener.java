package com.company.librarymanagement.loan.control.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BookEventListener {

    private static final Logger logger = LoggerFactory.getLogger(BookEventListener.class);

    @EventListener
    @Async
    public void handleAsyncBookReturnedEvent(BookReturnedEvent event) {
        logger.info("Handle book returned event: {}", event.getMessage());
    }
}
