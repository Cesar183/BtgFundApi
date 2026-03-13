package com.app.BtgFund.common;

/**
 * Exception for authentication/authorization failures.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Creates a new unauthorized exception.
     *
     * @param message error detail
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
