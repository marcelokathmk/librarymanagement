package com.company.librarymanagement.shared.exception;

import java.io.Serializable;

public class CustomError implements Serializable {

    private Integer httpStatus;

    private String message;

    private String path;

    public CustomError(Integer httpStatus, String message, String path) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
