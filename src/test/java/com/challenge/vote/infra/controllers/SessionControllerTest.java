package com.challenge.vote.infra.controllers;


import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.OpenSession;
import com.challenge.vote.infra.repositories.databases.SessionRepositoryDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.challenge.vote.util.HttpExceptionConstants.*;
import static com.challenge.vote.util.SessionControllerTestConstants.*;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SessionControllerTest implements VoteApplicationTests {

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
    void shouldCreateSession() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .build();
        mvc.perform(post(SESSION_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_PATH_SESSION_ID).value(1L))
                .andExpect(jsonPath(JSON_PATH_SESSION_DESCRIPTION).value("ANY_SESSION"))
                .andExpect(jsonPath(JSON_PATH_SESSION_DURATION).value(60L));
    }

    @Test
    void shouldGetSession() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(input);
        mvc.perform(get(format("%s/%d", SESSION_URL, createdSession.getSessionId()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_PATH_SESSION_ID).value(createdSession.getSessionId()))
                .andExpect(jsonPath(JSON_PATH_SESSION_DESCRIPTION).value("ANY_SESSION"))
                .andExpect(jsonPath(JSON_PATH_SESSION_DURATION).value(3600L));
    }

    @Test
    void shouldReturn404_whenSessionIdNotExists() throws Exception {
        mvc.perform(get(format("%s/%d", SESSION_URL, -1))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TIMESTAMP).exists())
                .andExpect(jsonPath(JSON_PATH_PATH).value(format("%s/%d", SESSION_URL, -1)))
                .andExpect(jsonPath(JSON_PATH_MESSAGE).value("Session not found."))
                .andExpect(jsonPath(JSON_PATH_CODE).value(NOT_FOUND.value()));
    }

    @Test
    void shouldOpenSession() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(input);
        mvc.perform(post(format("%s/open/%d", SESSION_URL, createdSession.getSessionId()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isOk());
        final var session = this.sessionRepositoryDatabase.findBySessionId(createdSession.getSessionId());
        assertTrue(session.isOpen(now()));
    }

    @Test
    void shouldReturn400_whenSessionIsAlreadyOpen() throws Exception {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(input);
        this.openSession.execute(createdSession.getSessionId());
        mvc.perform(post(format("%s/open/%d", SESSION_URL, createdSession.getSessionId()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_PATH_TIMESTAMP).exists())
                .andExpect(jsonPath(JSON_PATH_PATH).value(format("%s/open/%d", SESSION_URL, createdSession.getSessionId())))
                .andExpect(jsonPath(JSON_PATH_MESSAGE).value("Session is already open."))
                .andExpect(jsonPath(JSON_PATH_CODE).value(BAD_REQUEST.value()));
    }

    @Test
    void shouldReturn404_whenSessionIdNotExistsToOpen() throws Exception {
        mvc.perform(post(format("%s/open/%d", SESSION_URL, -1))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TIMESTAMP).exists())
                .andExpect(jsonPath(JSON_PATH_PATH).value(format("%s/open/%d", SESSION_URL, -1)))
                .andExpect(jsonPath(JSON_PATH_MESSAGE).value("Session not found."))
                .andExpect(jsonPath(JSON_PATH_CODE).value(NOT_FOUND.value()));
    }
}
