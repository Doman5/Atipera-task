package com.domanski.githubtask.exception;

public class UsernameNoExistException extends RuntimeException {
    public UsernameNoExistException(String message) {
        super(message);
    }
}
