package com.company.librarymanagement.fee.control;

import com.company.librarymanagement.fee.control.exception.FeeException;
import com.company.librarymanagement.fee.control.repository.FeeRepository;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.shared.exception.LibraryErrorMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FeeService {

    private final FeeRepository feeRepository;

    public FeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public Fee getFee() {
        return this.feeRepository.getFee();
    }

    public Fee updateFee(Long feeId, BigDecimal feeValue) {
        Fee fee = findFeeById(feeId);

        fee.setValue(feeValue);

        return this.feeRepository.save(fee);
    }

    private Fee findFeeById(Long feeId) {
        Optional<Fee> fee = this.feeRepository.findById(feeId);
        if (fee.isEmpty()) {
            throw new FeeException(LibraryErrorMessage.FEE_NOT_FOUND);
        }
        return fee.get();
    }
}
