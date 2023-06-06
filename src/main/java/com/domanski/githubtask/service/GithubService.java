package com.domanski.githubtask.service;

import com.domanski.githubtask.controller.dto.UserRepoResponse;
import com.domanski.githubtask.model.*;
import com.domanski.githubtask.model.dto.BranchResponse;
import com.domanski.githubtask.uttils.GitBranchDownloadable;
import com.domanski.githubtask.uttils.GitRepoDownloadable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GithubService {

    private final GitRepoDownloadable gitRepoDownloader;
    private final GitBranchDownloadable gitBranchDownloader;

    public List<UserRepoResponse> getUserRepositoriesWithBranches(String username) {
        List<GitRepo> userRepos = gitRepoDownloader.downloadGitRepos(username);
        return userRepos.stream()
                .filter(repo -> !repo.isFork())
                .map(GithubService::deleteRedundantBranchUrlEnding)
                .map(this::downloadAllRepoBranchesAndCreateUserRepoResponse
                ).toList();
    }

    private UserRepoResponse downloadAllRepoBranchesAndCreateUserRepoResponse(GitRepo repo) {
        List<BranchResponse> branches = gitBranchDownloader.downloadAllBranchesFromBranchesUrl(repo.getBranchesUrl());
        return createUserRepoResponse(repo, branches);
    }

    private static GitRepo deleteRedundantBranchUrlEnding(GitRepo repo) {
        return GitRepo.builder()
                .name(repo.getName())
                .owner(repo.getOwner())
                .fork(repo.isFork())
                .branchesUrl(repo.getBranchesUrl().substring(0, repo.getBranchesUrl().length() - 9))
                .build();
    }

    private static UserRepoResponse createUserRepoResponse(GitRepo repo, List<BranchResponse> branches) {
        return UserRepoResponse.builder()
                .repoName(repo.getName())
                .ownerLogin(repo.getOwner().login())
                .branches(branches)
                .build();
    }
}
