package com.isc.core.utils.exception;


public class BlankFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BlankFieldException(String msg) {
        super(msg);
    }
}
