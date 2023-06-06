package com.domanski.githubtask.uttils;

import com.domanski.githubtask.model.Branch;
import com.domanski.githubtask.model.dto.BranchResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class GitBranchDownloader implements GitBranchDownloadable {

    private final RestTemplate restTemplate;

    @Override
    public List<BranchResponse> downloadAllBranchesFromBranchesUrl(String branchesUrl) {
        ResponseEntity<Branch[]> branches = restTemplate.getForEntity(branchesUrl, Branch[].class);
        return Arrays.stream(Objects.requireNonNull(branches.getBody()))
                .map(GitBranchDownloader::createBranchResponse)
                .toList();
    }

    private static BranchResponse createBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .name(branch.name())
                .lastCommitSha(branch.commit()
                        .sha())
                .build();
    }
}
