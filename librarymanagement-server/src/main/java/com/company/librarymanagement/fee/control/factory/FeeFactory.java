package com.company.librarymanagement.fee.control.factory;

import com.company.librarymanagement.fee.entity.Fee;
import com.company.librarymanagement.server.api.model.FeeApiObject;

public final class FeeFactory {

    private FeeFactory() {}

    public static FeeApiObject fromEntityToApiResponse(Fee fee) {
        final FeeApiObject feeApiObject = new FeeApiObject();
        feeApiObject.setId(fee.getId());
        feeApiObject.setFeeValue(fee.getValue());
        return feeApiObject;
    }
}
