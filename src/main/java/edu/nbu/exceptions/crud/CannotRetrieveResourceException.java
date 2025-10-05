package edu.nbu.exceptions.crud;

public class CannotRetrieveResourceException extends RuntimeException {
    public CannotRetrieveResourceException(String message) {
        super(message);
    }
}
