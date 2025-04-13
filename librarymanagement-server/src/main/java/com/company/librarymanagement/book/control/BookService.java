package com.company.librarymanagement.book.control;

import com.company.librarymanagement.book.control.exception.BookException;
import com.company.librarymanagement.book.control.factory.BookFactory;
import com.company.librarymanagement.book.control.repository.BookRepository;
import com.company.librarymanagement.book.control.repository.BookSpecifications;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.server.api.model.BookApiRequest;
import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBookById(final Long bookId) {
        Optional<Book> existingBook = bookRepository.findByIdAndActiveIsTrue(bookId);
        if (existingBook.isEmpty()) {
            throw new BookException(LibraryErrorMessage.BOOK_NOT_FOUND);
        }
        return existingBook.get();
    }

    public Book createBook(final BookApiRequest bookApiRequest) {
        Book book = BookFactory.buildBookForCreation(bookApiRequest);
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(final Long bookId, final BookApiRequest bookApiRequest) {
        Book existingBook = findBookById(bookId);
        Book updatedBook = BookFactory.buildBookForUpdate(existingBook, bookApiRequest);
        return bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(final Long bookId) {
        Book bookForDeletion = findBookById(bookId);
        bookForDeletion.setActive(Boolean.FALSE);
        bookRepository.save(bookForDeletion);
    }

    public Page<Book> searchBooks(String title, String author, String genre, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Specification<Book> specification = Specification.where(BookSpecifications.hasTitle(title))
                .and(BookSpecifications.hasAuthor(author))
                .and(BookSpecifications.hasGenre(genre));

        return bookRepository.findAll(specification, pageable);
    }
}
