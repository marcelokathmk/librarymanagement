package com.company.librarymanagement.book.boundary;

import com.company.librarymanagement.book.control.BookService;
import com.company.librarymanagement.book.control.factory.BookFactory;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.server.api.model.BookApiRequest;
import com.company.librarymanagement.server.api.model.BookApiResponse;
import com.company.librarymanagement.server.api.model.BookApiResponseList;
import com.company.librarymanagement.server.services.BooksApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class BookResource implements BooksApi {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<BookApiResponse> addBook(BookApiRequest bookApiRequest) {
        logger.info("Entering on addBook, with title '{}'", bookApiRequest.getTitle());

        Book bookCreated = bookService.createBook(bookApiRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(BookFactory.fromEntityToApi(bookCreated));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Void> deleteBook(Long bookId) {
        logger.info("Entering on deleteBook, with bookId '{}'", bookId);

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<BookApiResponse> searchBook(Long bookId) {
        logger.info("Entering on searchBook, with bookId '{}'", bookId);

        Book book = bookService.findBookById(bookId);

        return ResponseEntity.ok(BookFactory.fromEntityToApi(book));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<BookApiResponseList> searchBooks(String title, String author, String genre, Integer page, Integer limit) {
        logger.info("Entering on searchBooks, with title: {}, author: {}, genre: {}, page: {}, limit: {}", title, author, genre, page, limit);

        Page<Book> bookPage = bookService.searchBooks(title, author, genre, page, limit);

        return ResponseEntity.ok()
                .headers(BookFactory.buildHttpHeadersForSearchBooks(bookPage))
                .body(BookFactory.fromEntityToApi(bookPage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<BookApiResponse> updateBook(Long bookId, BookApiRequest bookApiRequest) {
        logger.info("Entering on updateBook, with bookId '{}'", bookId);

        Book bookUpdated = bookService.updateBook(bookId, bookApiRequest);

        return ResponseEntity.ok(BookFactory.fromEntityToApi(bookUpdated));
    }



}
