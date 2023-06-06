package com.domanski.githubtask.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class GithubErrorResponse {
    HttpStatus status;
    String message;
}
