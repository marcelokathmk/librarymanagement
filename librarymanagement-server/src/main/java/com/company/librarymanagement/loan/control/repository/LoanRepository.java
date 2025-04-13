package com.company.librarymanagement.loan.control.repository;

import com.company.librarymanagement.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBookIdAndReturnDateIsNullAndReturnedIsFalse(Long bookId);

    List<Loan> findByBookId(Long bookId);
}
