package com.company.librarymanagement.loan.boundary;

import com.company.librarymanagement.fee.control.FeeService;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.loan.control.LoanService;
import com.company.librarymanagement.loan.control.factory.LoanFactory;
import com.company.librarymanagement.loan.entity.Loan;
import com.company.librarymanagement.server.api.model.LoanApiRequest;
import com.company.librarymanagement.server.api.model.LoanApiResponse;
import com.company.librarymanagement.server.api.model.LoanApiResponseList;
import com.company.librarymanagement.server.services.LoansApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class LoanResource implements LoansApi {

    private static final Logger logger = LoggerFactory.getLogger(LoanResource.class);

    private final LoanService loanService;

    private final FeeService feeService;

    public LoanResource(LoanService loanService, FeeService feeService) {
        this.loanService = loanService;
        this.feeService = feeService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<LoanApiResponseList> bookHistory(Long bookId) {
        logger.info("Entering on bookHistory, with book id '{}'", bookId);

        List<Loan> bookHistory = loanService.getBookHistory(bookId);

        Fee fee = feeService.getFee();

        return ResponseEntity.ok(LoanFactory.fromEntityToApiResponse(bookHistory, fee));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<LoanApiResponse> borrowBook(LoanApiRequest loanApiRequest) {
        logger.info("Entering on borrowBook, with book id '{}'", loanApiRequest.getBookId());

        final String userAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();

        Loan loan = loanService.borrowBook(loanApiRequest.getBookId(), userAuthenticated);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loan.getId())
                .toUri();

        return ResponseEntity.created(location).body(LoanFactory.fromEntityToApiResponse(loan));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<LoanApiResponse> returnBook(Long bookId) {
        logger.info("Entering on returnBook, with book id '{}'", bookId);

        final String userAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();

        Loan loan = loanService.returnBook(bookId, userAuthenticated);

        Fee fee = feeService.getFee();

        return ResponseEntity.ok(LoanFactory.fromEntityToApiResponse(loan, fee));
    }
}
