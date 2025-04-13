package com.company.librarymanagement.fee.control.repository;

import com.company.librarymanagement.fee.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query("SELECT f FROM Fee f")
    Fee getFee();
}
