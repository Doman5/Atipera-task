package com.domanski.githubtask.controller.dto;

import com.domanski.githubtask.model.dto.BranchResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.util.List;

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UserRepoResponse(String repoName,
        String ownerLogin,
        List<BranchResponse> branches) {
}

