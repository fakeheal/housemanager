package edu.nbu.exceptions;

public class CompanyNotSetupException extends RuntimeException {
    public CompanyNotSetupException() {
        super("You need to set up a company before performing this operation.");
    }
}
