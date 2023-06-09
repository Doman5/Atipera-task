package com.domanski.githubtask.model;

import lombok.Builder;

@Builder
public record Branch(String name, Commit commit) {
}
