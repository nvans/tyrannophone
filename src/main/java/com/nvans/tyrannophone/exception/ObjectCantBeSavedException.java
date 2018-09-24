package com.nvans.tyrannophone.exception;

public class ObjectCantBeSavedException extends TyrannophoneException {

    public ObjectCantBeSavedException() {
    }

    public ObjectCantBeSavedException(String message) {
        super(message);
    }

    public ObjectCantBeSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
