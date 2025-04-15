package com.company.librarymanagement.it.book;

import com.company.librarymanagement.book.control.repository.BookRepository;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.it.container.ContainerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@SpringBootTest
@Import(ContainerConfig.class)
public class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findBookTest() {
        Optional<Book> bookFromDb = bookRepository.findByIdAndActiveIsTrue(1L);

        Assertions.assertNotNull(bookFromDb);
        Assertions.assertTrue(bookFromDb.isPresent());
    }
}
