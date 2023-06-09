package com.domanski.githubtask.features;

import com.domanski.githubtask.GithubTaskApplication;
import com.domanski.githubtask.controller.dto.UserRepoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GithubTaskApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CommonFunctionIntegrationTest implements SampleRestTemplateResponses {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void should_return_github_responses_for_typical_app_use() throws Exception {
        //step 1: search user what exist on GitHub and return correct response
        //given
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/users/Doman5/repos")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(responseForExistingUserOnGithub()))
                );
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/repos/Doman5/Repo1/branches")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(branchesDownloadedResponseForRepo1()))
                );
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/repos/Doman5/Repo2/branches")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(branchesDownloadedResponseForRepo2()))
                );
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/repos/Doman5/Repo3/branches")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(branchesDownloadedResponseForRepo3()))
                );
        //when
        String responseForExistingUser = mockMvc.perform(MockMvcRequestBuilders.get("/github")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept", "application/json")
                        .content("""
                                Doman5
                                """.trim()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserRepoResponse> userRepos = objectMapper.readValue(responseForExistingUser, new TypeReference<>() {
        });
        //then
        assertAll(
                () -> assertThat(userRepos).hasSize(3),
                () -> assertThat(userRepos.get(0).branches()).hasSize(4),
                () -> assertThat(userRepos.get(0).repoName()).isEqualTo("Repo1"),
                () -> assertThat(userRepos.get(1).branches()).hasSize(1),
                () -> assertThat(userRepos.get(1).repoName()).isEqualTo("Repo2"),
                () -> assertThat(userRepos.get(2).branches()).hasSize(2),
                () -> assertThat(userRepos.get(2).repoName()).isEqualTo("Repo3")
        );
        mockServer.reset();
        //step 2: try search user who do not exist on GitHub
        //given
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/users/Doman5555/repos")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(("""
                                {
                                    "message": "Not Found",
                                    "documentation_url": "https://docs.github.com/rest/reference/repos#list-repositories-for-a-user"
                                }
                                """)))
                );
        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/github")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept", "application/json")
                        .content("""
                                Doman5555
                                """.trim()))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "status":"NOT_FOUND",
                        "message":"Profile with username Doman5555 no exist on github!"
                        }
                        """.trim()));
        mockServer.reset();
        // step 3 try to request with header accept: "application/xml" and return error with message
        //given && when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/github")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept", "application/xml")
                        .content("""
                                Doman5
                                """.trim()))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().json("""
                                {
                                    "status": "NOT_ACCEPTABLE",
                                    "message": "application/xml is unsupported media type"
                                }
                        """.trim()));

        // step 4 search user what exist on GitHub but have not any repositories and return 409 conflict and response with message
        //given
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api.github.com/users/Doman55/repos")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("[]"));
        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/github")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept", "application/json")
                        .content("""
                                Doman55
                                """.trim()))
                .andExpect(status().isConflict())
                .andExpect(content().json("""
                                {
                                    "status": "CONFLICT",
                                    "message": "User do not have any repositories on this profile!"
                                }
                        """.trim()));
    }
}

