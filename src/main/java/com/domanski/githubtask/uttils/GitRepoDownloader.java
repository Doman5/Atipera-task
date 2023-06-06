package com.domanski.githubtask.uttils;

import com.domanski.githubtask.exception.UserDoNotHaveRepositoriesException;
import com.domanski.githubtask.exception.UsernameNoExistException;
import com.domanski.githubtask.model.GitRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class GitRepoDownloader implements GitRepoDownloadable {

    private final RestTemplate restTemplate;

    @Override
    public List<GitRepo> downloadGitRepos(String username) {
        String url = String.format("https://api.github.com/users/%s/repos", username);
        try {
            GitRepo[] downloadedUserRepos = restTemplate.getForEntity(url, GitRepo[].class).getBody();
            if (downloadedUserRepos != null && downloadedUserRepos.length != 0) {
                return Arrays.stream(downloadedUserRepos).toList();
            } else {
                throw new UserDoNotHaveRepositoriesException("User do not have any repositories on this profile!");
            }
        } catch (HttpClientErrorException e) {
            throw new UsernameNoExistException("Profile with username %s no exist on github!".formatted(username));
        }
    }
}
