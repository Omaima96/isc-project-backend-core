package com.isc.core.utils.exception;

import lombok.Data;

@Data
public class ExceptionMessage {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

}
