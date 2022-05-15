package com.challenge.vote.infra.controllers;


import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SessionRepositoryDatabase sessionRepositoryDatabase;

    @Autowired
    private CreateSession createSession;

    private MockMvc mvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.sessionRepositoryDatabase.clean();
    }

    @Test
    void shouldCreateSession() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .build();
        mvc.perform(post("/v1/session")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sessionId").value(1L))
                .andExpect(jsonPath("$.sessionDescription").value("ANY_SESSION"))
                .andExpect(jsonPath("$.sessionDuration").value(60L));
    }

    @Test
    void shouldGetSession() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(input);
        mvc.perform(get("/v1/session" + "/" + createdSession.getSessionId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(createdSession.getSessionId()))
                .andExpect(jsonPath("$.sessionDescription").value("ANY_SESSION"))
                .andExpect(jsonPath("$.sessionDuration").value(3600L));
    }
}
