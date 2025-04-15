package com.company.librarymanagement.fee.boundary;

import com.company.librarymanagement.fee.control.FeeService;
import com.company.librarymanagement.fee.control.factory.FeeFactory;
import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.server.api.model.FeeApiObject;
import com.company.librarymanagement.server.services.FeesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeeResource implements FeesApi {

    private static final Logger logger = LoggerFactory.getLogger(FeeResource.class);

    private final FeeService feeService;

    public FeeResource(FeeService feeService) {
        this.feeService = feeService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<FeeApiObject> getFee() {
        logger.info("Entering on getFee.");

        Fee fee = feeService.getFee();

        return ResponseEntity.ok(FeeFactory.fromEntityToApiResponse(fee));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<FeeApiObject> updateFee(FeeApiObject feeApiObject) {
        logger.info("Entering on updateFee.");

        Fee fee = feeService.updateFee(feeApiObject.getId(), feeApiObject.getFeeValue());

        return ResponseEntity.ok(FeeFactory.fromEntityToApiResponse(fee));
    }
}
