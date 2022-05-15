package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.OpenSession;
import com.challenge.vote.application.usecases.vote.DoVoteInput;
import com.challenge.vote.infra.repositories.databases.SessionRepositoryDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SessionRepositoryDatabase sessionRepositoryDatabase;

    @Autowired
    private CreateSession createSession;

    @Autowired
    private OpenSession openSession;

    private MockMvc mvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.sessionRepositoryDatabase.clean();
    }

    @Test
    void shouldRegisterVote() throws Exception {
        final var sessionInput = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(sessionInput);
        this.openSession.execute(createdSession.getSessionId());
        final var input = DoVoteInput.builder()
                .sessionId(createdSession.getSessionId())
                .inFavor(Boolean.TRUE)
                .cpf("ANY_CPF")
                .build();
        mvc.perform(post("/v1/vote")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
