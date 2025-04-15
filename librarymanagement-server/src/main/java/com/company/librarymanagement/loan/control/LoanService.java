package com.company.librarymanagement.loan.control;

import com.company.librarymanagement.auth.control.AuthService;
import com.company.librarymanagement.auth.entity.User;
import com.company.librarymanagement.book.control.BookService;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.loan.control.exception.LoanException;
import com.company.librarymanagement.loan.control.factory.LoanFactory;
import com.company.librarymanagement.loan.control.repository.LoanRepository;
import com.company.librarymanagement.loan.entity.Loan;
import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing book loans.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final AuthService authService;

    private final BookService bookService;

    public LoanService(LoanRepository loanRepository, AuthService authService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.authService = authService;
        this.bookService = bookService;
    }

    /**
     * Method responsible for processing the loan of a specific book.
     * If the book is not available, it throws an exception; otherwise, it proceeds with the loan.
     *
     * @param bookId the book id
     * @param userLogin the user login
     * @return The loan details
     */
    @Transactional
    public Loan borrowBook(Long bookId, String userLogin) {
        User user = authService.findUserByLogin(userLogin);

        LocalDateTime loanDate = LocalDateTime.now();
        LocalDateTime dueDate = loanDate.plusWeeks(1);

        Book book = getBookIfAvailableForLoan(bookId);

        Loan loan = LoanFactory.buildLoanForCreation(loanDate, dueDate, user, book);

        return loanRepository.save(loan);
    }

    /**
     * Checks the availability of a specific book and returns it if available;
     * otherwise, it throws an exception.
     *
     * @param bookId The book id
     * @return The book
     */
    private Book getBookIfAvailableForLoan(final Long bookId) {
        Book book = bookService.findBookById(bookId);

        List<Loan> bookLoans = loanRepository.findByBookIdAndReturnDateIsNullAndReturnedIsFalse(bookId);
        if (bookLoans != null && !bookLoans.isEmpty()) {
            throw new LoanException(LibraryErrorMessage.BOOK_ALREADY_LOANED);
        }

        return book;
    }

    /**
     * Service responsible for managing the return of a book.
     * Searches for loans for a specific book.
     * Verify if the loan belongs to the given user login
     *
     * @param bookId The book id
     * @param userLogin The user login
     * @return The loan with the returned date and returned flag value updated.
     */
    @Transactional
    public Loan returnBook(Long bookId, String userLogin) {
        List<Loan> bookLoans = loanRepository.findByBookIdAndReturnDateIsNullAndReturnedIsFalse(bookId);

        if (bookLoans != null && bookLoans.isEmpty()) {
            throw new LoanException(LibraryErrorMessage.BOOK_NOT_LOANED);
        }

        User user = authService.findUserByLogin(userLogin);

        Optional<Loan> optionalLoan = bookLoans.stream()
                .filter(l -> l.getUser().getId().equals(user.getId()))
                .findFirst();

        if (optionalLoan.isEmpty()) {
            throw new LoanException(LibraryErrorMessage.BOOK_LOANED_BY_ANOTHER_USER);
        }

        Loan loan = optionalLoan.get();
        loan.setReturnDate(LocalDateTime.now());
        loan.setReturned(Boolean.TRUE);

        return loanRepository.save(loan);
    }

    /**
     * Fetches the loan history of a specific book.
     *
     * @param bookId the book id
     * @return the loan history
     */
    public List<Loan> getBookHistory(final Long bookId) {
        return loanRepository.findByBookId(bookId);
    }
}
