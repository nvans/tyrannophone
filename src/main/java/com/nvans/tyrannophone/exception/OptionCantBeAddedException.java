package com.nvans.tyrannophone.exception;

public class OptionCantBeAddedException extends TyrannophoneException {

    public OptionCantBeAddedException() {
    }

    public OptionCantBeAddedException(String message) {
        super(message);
    }

    public OptionCantBeAddedException(String message, Throwable cause) {
        super(message, cause);
    }
}
