package com.domanski.githubtask.controller.dto;

import com.domanski.githubtask.model.dto.BranchResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.util.List;

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserRepoResponse {
    String repoName;
    String ownerLogin;
    List<BranchResponse> branches;

    public UserRepoResponse(String repoName, String ownerLogin, List<BranchResponse> branches) {
        this.repoName = repoName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }
}
