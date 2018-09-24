package com.nvans.tyrannophone.exception;

public class PlanAlreadyExistException extends TyrannophoneException {

    public PlanAlreadyExistException() {
    }

    public PlanAlreadyExistException(String message) {
        super(message);
    }

    public PlanAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
