package com.app.BtgFund.common;

/**
 * Exception for domain/business-rule violations.
 */
public class BusinessException extends RuntimeException {

    /**
     * Creates a new business exception.
     *
     * @param message error detail
     */
    public BusinessException(String message) {
        super(message);
    }
}
