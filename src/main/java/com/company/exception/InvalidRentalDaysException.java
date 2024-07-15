package com.company.exception;

/**
 * Exception thrown when the rental day count is invalid.
 */
public class InvalidRentalDaysException extends IllegalArgumentException {

    /**
     * Constructs a new InvalidRentalDaysException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidRentalDaysException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidRentalDaysException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public InvalidRentalDaysException(String message, Throwable cause) {
        super(message, cause);
    }
}
