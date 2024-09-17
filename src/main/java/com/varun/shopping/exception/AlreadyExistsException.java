package com.varun.shopping.exception;

public class AlreadyExistsException extends  RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }
}
