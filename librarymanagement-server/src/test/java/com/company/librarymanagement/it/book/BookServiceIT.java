package com.company.librarymanagement.it.book;

import com.company.librarymanagement.book.control.BookService;
import com.company.librarymanagement.book.control.exception.BookException;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.it.container.ContainerConfig;
import com.company.librarymanagement.server.api.model.BookApiRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@SpringBootTest
@Import(ContainerConfig.class)
public class BookServiceIT {

    @Autowired
    private BookService bookService;

    @Test
    public void findBookById_WhenExistsOnDatabaseTest() {
        Book book = bookService.findBookById(1L);

        Assertions.assertNotNull(book);
    }

    @Test
    public void findBookById_WhenDoesNotExistOnDatabaseTest() {
        Assertions.assertThrows(BookException.class,
                () -> bookService.findBookById(100L));
    }

    @Test
    public void createBookTest() {
        BookApiRequest newBookRequest = new BookApiRequest();
        newBookRequest.setTitle("Title - IT");
        newBookRequest.setAuthor("Author - IT");
        newBookRequest.setGenre("Genre - IT");
        newBookRequest.setNotes("Notes - IT");
        newBookRequest.setPublicationYear(LocalDate.now().getYear());

        Book savedBook = bookService.createBook(newBookRequest);

        Assertions.assertNotNull(savedBook, "Expected not null");
        Assertions.assertEquals(newBookRequest.getTitle(), savedBook.getTitle(), "Same value expected");
        Assertions.assertEquals(newBookRequest.getAuthor(), savedBook.getAuthor(), "Same value expected");
        Assertions.assertEquals(newBookRequest.getGenre(), savedBook.getGenre(), "Same value expected");
        Assertions.assertEquals(newBookRequest.getNotes(), savedBook.getNotes(), "Same value expected");
        Assertions.assertEquals(newBookRequest.getPublicationYear(), savedBook.getPublicationYear(), "Same value expected");
    }
}
