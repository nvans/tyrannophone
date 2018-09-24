package com.nvans.tyrannophone.exception;

public class TyrannophoneException extends RuntimeException {

    public TyrannophoneException() {
    }

    public TyrannophoneException(String message) {
        super(message);
    }

    public TyrannophoneException(String message, Throwable cause) {
        super(message, cause);
    }
}
