package com.company.exception;

/**
 * Exception thrown when the tool code is invalid.
 */
public class InvalidToolCodeException extends IllegalArgumentException {

    /**
     * Constructs a new InvalidToolCodeException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidToolCodeException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidToolCodeException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public InvalidToolCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
