package com.domanski.githubtask.controller;

import com.domanski.githubtask.exception.UnsupportedMediaTypeException;
import com.domanski.githubtask.controller.dto.UserRepoResponse;
import com.domanski.githubtask.service.GithubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping(value = "/github/{username}")
    public ResponseEntity<List<UserRepoResponse>> getUserRepositoryWithRequestParam(@PathVariable String username,
                                                                    @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new UnsupportedMediaTypeException("%s is unsupported media type".formatted(acceptHeader));
        }

        List<UserRepoResponse> finalResponses = githubService.getUserRepositoriesWithBranches(username);
        return ResponseEntity.ok(finalResponses);
    }

    @GetMapping(value = "/github")
    public ResponseEntity<List<UserRepoResponse>> getUserRepositoryWithRequestBody(@RequestBody String username,
                                                                    @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new UnsupportedMediaTypeException("%s is unsupported media type".formatted(acceptHeader));
        }

        List<UserRepoResponse> finalResponses = githubService.getUserRepositoriesWithBranches(username);
        return ResponseEntity.ok(finalResponses);
    }
}
