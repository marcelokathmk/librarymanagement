package com.company.librarymanagement.loan.control.factory;

import com.company.librarymanagement.auth.entity.User;
import com.company.librarymanagement.book.control.factory.BookFactory;
import com.company.librarymanagement.book.entity.Book;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.loan.entity.Loan;
import com.company.librarymanagement.server.api.model.LoanApiResponse;
import com.company.librarymanagement.server.api.model.LoanApiResponseList;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class LoanFactory {

    private LoanFactory() {}

    public static Loan buildLoanForCreation(final LocalDateTime loanDate,
                                            final LocalDateTime dueDate,
                                            final User user,
                                            final Book book) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);
        loan.setReturned(Boolean.FALSE);

        return loan;
    }

    public static LoanApiResponse fromEntityToApiResponse(final Loan loan, final Fee fee) {
        LoanApiResponse response = fromEntityToApiResponse(loan);
        response.setLateReturnFee(loan.getLateReturnFee(fee.getValue()));

        return response;
    }

    public static LoanApiResponse fromEntityToApiResponse(final Loan loan) {
        LoanApiResponse response = new LoanApiResponse();
        response.setId(loan.getId());
        response.setLoanDate(loan.getLoanDate());
        response.setDueDate(loan.getDueDate());
        response.setReturnDate(loan.getReturnDate());
        response.setUserName(loan.getUser().getName());
        response.setBook(BookFactory.fromEntityToApi(loan.getBook()));
        response.setIsLateReturn(loan.isLateReturn());

        return response;
    }

    public static LoanApiResponseList fromEntityToApiResponse(final List<Loan> bookHistory, final Fee fee) {
        LoanApiResponseList response = new LoanApiResponseList();
        response.loans(Collections.emptyList());

        if (!bookHistory.isEmpty()) {
            response.loans(bookHistory.stream()
                    .map(l -> fromEntityToApiResponse(l, fee))
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
