package com.domanski.githubtask.exception;

public class GithubServerException extends RuntimeException {
    public GithubServerException(String message) {
        super(message);
    }
}
