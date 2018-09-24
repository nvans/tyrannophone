package com.nvans.tyrannophone.exception;

public class PlanCantBeDeletedException extends TyrannophoneException {

    public PlanCantBeDeletedException() {
    }

    public PlanCantBeDeletedException(String message) {
        super(message);
    }

    public PlanCantBeDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
