package com.company.librarymanagement.book.control.factory;

import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.server.api.model.BookApiRequest;
import com.company.librarymanagement.server.api.model.BookApiResponse;
import com.company.librarymanagement.server.api.model.BookApiResponseList;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class responsible to build Book objects from Entity or Api.
 */
public final class BookFactory {

    private BookFactory() {}

    public static Book buildBookForCreation(BookApiRequest bookApiRequest) {
        final Book entity = new Book();
        entity.setTitle(bookApiRequest.getTitle());
        entity.setAuthor(bookApiRequest.getAuthor());
        entity.setPublicationYear(bookApiRequest.getPublicationYear());
        entity.setGenre(bookApiRequest.getGenre());
        entity.setNotes(bookApiRequest.getNotes());
        entity.setActive(Boolean.TRUE);
        return entity;
    }


    public static BookApiResponse fromEntityToApi(Book bookCreated) {
        final BookApiResponse response = new BookApiResponse();
        response.setId(bookCreated.getId());
        response.setTitle(bookCreated.getTitle());
        response.setAuthor(bookCreated.getAuthor());
        response.setPublicationYear(bookCreated.getPublicationYear());
        response.setGenre(bookCreated.getGenre());
        response.setNotes(bookCreated.getNotes());
        return response;
    }

    public static Book buildBookForUpdate(Book book, BookApiRequest bookApiRequest) {
        if (Strings.isNotEmpty(bookApiRequest.getTitle())) {
            book.setTitle(bookApiRequest.getTitle());
        }
        if (Strings.isNotEmpty(bookApiRequest.getAuthor())) {
            book.setAuthor(bookApiRequest.getAuthor());
        }
        if (bookApiRequest.getPublicationYear() != null) {
            book.setPublicationYear(bookApiRequest.getPublicationYear());
        }
        if (Strings.isNotEmpty(bookApiRequest.getGenre())) {
            book.setGenre(bookApiRequest.getGenre());
        }
        if (Strings.isNotEmpty(bookApiRequest.getNotes())) {
            book.setNotes(bookApiRequest.getNotes());
        }

        return book;
    }

    public static BookApiResponseList fromEntityToApi(Page<Book> bookPage) {
        BookApiResponseList response = new BookApiResponseList();
        response.books(Collections.emptyList());

        if (bookPage.hasContent()) {
            response.books(bookPage.getContent().stream()
                    .map(BookFactory::fromEntityToApi)
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
