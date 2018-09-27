package com.nvans.tyrannophone.exception;

public class IncompatibleOptionsException extends TyrannophoneException {

    public IncompatibleOptionsException() {
    }

    public IncompatibleOptionsException(String message) {
        super(message);
    }

    public IncompatibleOptionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
