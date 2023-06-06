package com.domanski.githubtask.uttils;

import com.domanski.githubtask.model.dto.BranchResponse;

import java.util.List;

public interface GitBranchDownloadable {
    List<BranchResponse> downloadAllBranchesFromBranchesUrl(String branchUrl);
}
