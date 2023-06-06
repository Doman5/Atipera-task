package com.domanski.githubtask.controller;

import com.domanski.githubtask.exception.UnsupportedMediaTypeException;
import com.domanski.githubtask.exception.UserDoNotHaveRepositoriesException;
import com.domanski.githubtask.exception.UsernameNoExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = GithubController.class)
public class GithubControllerErrorHandler {

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    @ResponseBody
    public ResponseEntity<GithubErrorResponse> handleUnsupportedMediaTypeException(UnsupportedMediaTypeException ex) {
        GithubErrorResponse error = new GithubErrorResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(UserDoNotHaveRepositoriesException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<GithubErrorResponse> handleUserDoNotHaveRepoException(UserDoNotHaveRepositoriesException e) {
        GithubErrorResponse responseError = new GithubErrorResponse(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(responseError);
    }

    @ExceptionHandler(UsernameNoExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GithubErrorResponse> handleUsernameNoExistException(UsernameNoExistException e) {
        GithubErrorResponse responseError = new GithubErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }


}
