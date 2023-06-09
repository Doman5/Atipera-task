package com.domanski.githubtask.uttils;

import com.domanski.githubtask.exception.GithubServerException;
import com.domanski.githubtask.exception.UserDoNotHaveRepositoriesException;
import com.domanski.githubtask.exception.UsernameNoExistException;
import com.domanski.githubtask.model.GitRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GitRepoDownloader implements GitRepoDownloadable {

    private final RestTemplate restTemplate;
    private final String GITHUB_USER_REPO_URL;

    public GitRepoDownloader(RestTemplate restTemplate,@Value("${app.github.user.repo.url}") String url) {
        this.restTemplate = restTemplate;
        this.GITHUB_USER_REPO_URL = url;
    }

    @Override
    public List<GitRepo> downloadGitRepos(String username) {
        String url = String.format(GITHUB_USER_REPO_URL, username);
        try {
            GitRepo[] downloadedUserRepos = restTemplate.getForEntity(url, GitRepo[].class).getBody();
            if (downloadedUserRepos != null && downloadedUserRepos.length != 0) {
                return Arrays.stream(downloadedUserRepos).toList();
            } else {
                throw new UserDoNotHaveRepositoriesException("User do not have any repositories on this profile!");
            }
        } catch (HttpClientErrorException e) {
            throw new UsernameNoExistException("Profile with username %s no exist on github!".formatted(username));
        } catch (HttpServerErrorException e) {
            throw new GithubServerException("Problem with external service, please try letter");
        }
    }
}
