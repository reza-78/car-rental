package com.mohaymen.internship.carrental.common.exception;

public class NoDriverToAssignException extends RuntimeException {
    public NoDriverToAssignException(int customerId){
        super("there is no driver for assigning to customer with id " + customerId);
    }
}
