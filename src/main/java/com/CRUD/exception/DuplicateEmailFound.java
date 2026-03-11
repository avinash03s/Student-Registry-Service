package com.CRUD.exception;

public class DuplicateEmailFound extends RuntimeException {
    DuplicateEmailFound(){}
    public DuplicateEmailFound(String message) {
        super(message);
    }
}
