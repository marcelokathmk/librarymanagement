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

/**
 * Service responsible for managing books in the inventory.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Fetches a book by its unique identifier.
     *
     * @param bookId the book id
     * @return the book
     */
    public Book findBookById(final Long bookId) {
        Optional<Book> existingBook = bookRepository.findByIdAndActiveIsTrue(bookId);
        if (existingBook.isEmpty()) {
            throw new BookException(LibraryErrorMessage.BOOK_NOT_FOUND);
        }
        return existingBook.get();
    }

    /**
     * Creates a new book in the inventory.
     *
     * @param bookApiRequest the api request object
     * @return the created book
     */
    public Book createBook(final BookApiRequest bookApiRequest) {
        Book book = BookFactory.buildBookForCreation(bookApiRequest);
        return bookRepository.save(book);
    }

    /**
     * Updates the book details.
     *
     * @param bookId the book id
     * @param bookApiRequest the api request object
     * @return the updated book.
     */
    @Transactional
    public Book updateBook(final Long bookId, final BookApiRequest bookApiRequest) {
        Book existingBook = findBookById(bookId);
        Book updatedBook = BookFactory.buildBookForUpdate(existingBook, bookApiRequest);
        return bookRepository.save(updatedBook);
    }

    /**
     * Logically deletes a book by changing the 'active' field to false.
     *
     * @param bookId the book id
     */
    @Transactional
    public void deleteBook(final Long bookId) {
        Book bookForDeletion = findBookById(bookId);
        bookForDeletion.setActive(Boolean.FALSE);
        bookRepository.save(bookForDeletion);
    }

    /**
     * Fetches books based on the provided filters.
     * The filters do not need to have exact values, as the 'like' mechanism is used in the database queries.
     * The query is paginated, allowing the limitation of the number of books returned, and also supports searching by a specific page.
     *
     * @param title the title
     * @param author the author
     * @param genre the genre
     * @param page the page
     * @param limit the limit of rows to be returned
     * @return the page of books
     */
    public Page<Book> searchBooks(String title, String author, String genre, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Specification<Book> specification = Specification.where(BookSpecifications.hasTitle(title))
                .and(BookSpecifications.hasAuthor(author))
                .and(BookSpecifications.hasGenre(genre));

        return bookRepository.findAll(specification, pageable);
    }
}
