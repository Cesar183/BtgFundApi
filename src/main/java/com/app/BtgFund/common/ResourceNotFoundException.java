package com.app.BtgFund.common;

/**
 * Exception for missing resources in persistence layer.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new resource-not-found exception.
     *
     * @param message error detail
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
