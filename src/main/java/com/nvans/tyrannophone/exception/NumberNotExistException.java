package com.nvans.tyrannophone.exception;

public class NumberNotExistException extends TyrannophoneException {

    public NumberNotExistException() {
    }

    public NumberNotExistException(String message) {
        super(message);
    }

    public NumberNotExistException(String message, Throwable cause) {
        super(message, cause);
    }


}
