package edu.nbu.exceptions.crud;

public class CannotUpdateResourceException extends RuntimeException {
    public CannotUpdateResourceException(String message) {
        super(message);
    }
}
