package edu.nbu.exceptions;

public class FeesAlreadyIssued extends RuntimeException {
    public FeesAlreadyIssued() {
        super("Fees already issued for the specified period");
    }
}
