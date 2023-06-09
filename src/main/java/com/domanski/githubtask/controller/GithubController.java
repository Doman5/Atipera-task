package com.domanski.githubtask.controller;


import com.domanski.githubtask.controller.dto.UserRepoResponse;
import com.domanski.githubtask.service.GithubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
public class GithubController {

    private final GithubService githubService;


    @GetMapping(value = "/github")
    public ResponseEntity<List<UserRepoResponse>> getUserRepositoryWithRequestBody(@RequestBody String username,
                                                                    @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new HttpMediaTypeNotAcceptableException("%s is unsupported media type".formatted(acceptHeader));
        }

        List<UserRepoResponse> finalResponses = githubService.getUserRepositoriesWithBranches(username);
        return ResponseEntity.ok(finalResponses);
    }
}
