package com.company.librarymanagement.it.fee;

import com.company.librarymanagement.fee.control.repository.FeeRepository;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.it.container.ContainerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Import(ContainerConfig.class)
public class FeeRepositoryIT {

    @Autowired
    private FeeRepository feeRepository;

    @Test
    public void getFeeTest() {
        Fee fee = feeRepository.getFee();

        Assertions.assertNotNull(fee);
        Assertions.assertEquals(new BigDecimal("1.50"), fee.getValue());
    }
}
