package com.company.librarymanagement.it.fee;

import com.company.librarymanagement.fee.control.FeeService;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.it.container.ContainerConfig;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@SpringBootTest
@Import(ContainerConfig.class)
public class FeeServiceIT {

    @Autowired
    private FeeService feeService;

    @Test
    public void getFeeTest() {
        Fee fee = feeService.getFee();

        Assertions.assertNotNull(fee, "Not null expected");
        Assertions.assertEquals(new BigDecimal("1.50"), fee.getValue(), "Same value expected");
    }

    @Test
    public void updateFeeTest() {
        Fee fee = feeService.getFee();

        Assertions.assertNotNull(fee, "Not null expected");

        BigDecimal newFeeValue = new BigDecimal("1.55");

        Fee updatedFee = feeService.updateFee(fee.getId(), newFeeValue);

        Assertions.assertNotNull(updatedFee, "Not null expected");
        Assertions.assertEquals(newFeeValue, updatedFee.getValue(), "Same value expected");
    }
}
