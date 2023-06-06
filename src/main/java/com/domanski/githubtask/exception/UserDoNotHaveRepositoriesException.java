package com.domanski.githubtask.exception;

public class UserDoNotHaveRepositoriesException extends RuntimeException {
    public UserDoNotHaveRepositoriesException(String message) {
        super(message);
    }
}
