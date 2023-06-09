package com.domanski.githubtask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GitRepo(String name,
                      RepoOwner owner,
                      boolean fork,
                      @JsonProperty("branches_url") String branchesUrl) {}
