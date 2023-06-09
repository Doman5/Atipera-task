package com.domanski.githubtask.model;

import lombok.Builder;

@Builder
public record Commit(String sha) {
}
