package com.domanski.githubtask.model.dto;

import lombok.Builder;

@Builder
public record BranchResponse(String name, String lastCommitSha) {
}
