package com.domanski.githubtask.uttils;

import com.domanski.githubtask.model.GitRepo;

import java.util.List;

public interface GitRepoDownloadable {
    List<GitRepo> downloadGitRepos(String username);
}
