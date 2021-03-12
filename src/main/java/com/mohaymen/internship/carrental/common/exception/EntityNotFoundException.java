package com.mohaymen.internship.carrental.common.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityType, int id){
        super(entityType + " with id " + id + " not found");
    }
}
