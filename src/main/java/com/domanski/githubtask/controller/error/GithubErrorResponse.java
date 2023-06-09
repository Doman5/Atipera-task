package com.domanski.githubtask.controller.error;

import org.springframework.http.HttpStatus;

public record GithubErrorResponse(HttpStatus status,
                                  String message) {
}
