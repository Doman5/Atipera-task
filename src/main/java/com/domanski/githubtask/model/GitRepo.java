package com.domanski.githubtask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class GitRepo {
    private String name;
    private RepoOwner owner;
    private boolean fork;
    @JsonProperty("branches_url")
    private String branchesUrl;


}
