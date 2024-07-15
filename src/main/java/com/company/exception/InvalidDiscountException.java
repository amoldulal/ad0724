package com.company.exception;

/**
 * Exception thrown when the discount percent is invalid.
 */
public class InvalidDiscountException extends IllegalArgumentException {

    /**
     * Constructs a new InvalidDiscountException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidDiscountException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidDiscountException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public InvalidDiscountException(String message, Throwable cause) {
        super(message, cause);
    }
}
