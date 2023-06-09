package com.domanski.githubtask.controller.error;

import com.domanski.githubtask.controller.GithubController;
import com.domanski.githubtask.exception.UserDoNotHaveRepositoriesException;
import com.domanski.githubtask.exception.UsernameNoExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = GithubController.class)
public class GithubControllerErrorHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResponseEntity<GithubErrorResponse> handleUnsupportedMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
        GithubErrorResponse error = new GithubErrorResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(UserDoNotHaveRepositoriesException.class)
    @ResponseBody

    public ResponseEntity<GithubErrorResponse> handleUserDoNotHaveRepoException(UserDoNotHaveRepositoriesException e) {
        GithubErrorResponse responseError = new GithubErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseError);
    }

    @ExceptionHandler(UsernameNoExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GithubErrorResponse> handleUsernameNoExistException(UsernameNoExistException e) {
        GithubErrorResponse responseError = new GithubErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }


}
