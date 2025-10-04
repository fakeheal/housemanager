package edu.nbu.exceptions.crud;

public class CannotCreateResourceException extends RuntimeException {
    public CannotCreateResourceException(String message) {
        super(message);
    }
}
