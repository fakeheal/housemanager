package edu.nbu.exceptions.crud;

public class CannotDeleteResourceException extends RuntimeException {
    public CannotDeleteResourceException(String message) {
        super(message);
    }
}
