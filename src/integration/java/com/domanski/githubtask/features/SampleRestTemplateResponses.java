package com.domanski.githubtask.features;

import com.domanski.githubtask.model.Branch;
import com.domanski.githubtask.model.Commit;
import com.domanski.githubtask.model.GitRepo;
import com.domanski.githubtask.model.RepoOwner;

public interface SampleRestTemplateResponses {

    default GitRepo[] responseForExistingUserOnGithub() {
        return new GitRepo[]{GitRepo.builder()
                .name("Repo1")
                .fork(false)
                .owner(RepoOwner.builder()
                        .login("Doman5")
                        .build())
                .branchesUrl("https://api.github.com/repos/Doman5/Repo1/branches{/branch}")
                .build(),
                GitRepo.builder()
                        .name("Repo2")
                        .fork(false)
                        .owner(RepoOwner.builder()
                                .login("Doman5")
                                .build())
                        .branchesUrl("https://api.github.com/repos/Doman5/Repo2/branches{/branch}")
                        .build(),
                GitRepo.builder()
                        .name("Repo3")
                        .fork(false)
                        .owner(RepoOwner.builder()
                                .login("Doman5")
                                .build())
                        .branchesUrl("https://api.github.com/repos/Doman5/Repo3/branches{/branch}")
                        .build(),
        };
    }

    default Branch[] branchesDownloadedResponseForRepo1() {
        return new Branch[]{Branch.builder()
                .name("First branch")
                .commit(Commit.builder()
                        .sha("1")
                        .build())
                .build(),
                Branch.builder()
                        .name("Second branch")
                        .commit(Commit.builder()
                                .sha("2")
                                .build())
                        .build(),
                Branch.builder()
                        .name("Third branch")
                        .commit(Commit.builder()
                                .sha("3")
                                .build())
                        .build(),
                Branch.builder()
                        .name("Fourth branch")
                        .commit(Commit.builder()
                                .sha("4")
                                .build())
                        .build(),
        };
    }

    default Branch[] branchesDownloadedResponseForRepo2() {
        return new Branch[]{Branch.builder()
                .name("First branch")
                .commit(Commit.builder()
                        .sha("1")
                        .build())
                .build()
        };
    }

    default Branch[] branchesDownloadedResponseForRepo3() {
        return new Branch[]{Branch.builder()
                .name("First branch")
                .commit(Commit.builder()
                        .sha("1")
                        .build())
                .build(),
                Branch.builder()
                        .name("Second branch")
                        .commit(Commit.builder()
                                .sha("2")
                                .build())
                        .build()
        };
    }
}
