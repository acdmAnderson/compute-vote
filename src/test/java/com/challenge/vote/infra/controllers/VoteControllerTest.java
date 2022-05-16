package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.OpenSession;
import com.challenge.vote.application.usecases.vote.DoVote;
import com.challenge.vote.application.usecases.vote.DoVoteInput;
import com.challenge.vote.infra.repositories.databases.SessionRepositoryDatabase;
import com.challenge.vote.infra.repositories.databases.VoteRepositoryDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.challenge.vote.util.HttpExceptionConstants.*;
import static com.challenge.vote.util.VoteControllerTestConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private VoteRepositoryDatabase voteRepositoryDatabase;

    @Autowired
    private CreateSession createSession;

    @Autowired
    private OpenSession openSession;

    @Autowired
    private DoVote doVote;

    private MockMvc mvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.sessionRepositoryDatabase.clean();
        this.voteRepositoryDatabase.clean();
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
                .inFavor(TRUE)
                .cpf("ANY_CPF")
                .build();
        mvc.perform(post(VOTE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn404_whenSessionIdNotExists() throws Exception {
        final var input = DoVoteInput.builder()
                .sessionId(-1L)
                .inFavor(TRUE)
                .cpf("ANY_CPF")
                .build();
        mvc.perform(post(VOTE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TIMESTAMP).exists())
                .andExpect(jsonPath(JSON_PATH_PATH).value(VOTE_URL))
                .andExpect(jsonPath(JSON_PATH_MESSAGE).value("Session not found."))
                .andExpect(jsonPath(JSON_PATH_CODE).value(NOT_FOUND.value()));
    }

    @Test
    void shouldReturn400_whenSessionIdNotOpen() throws Exception {
        final var sessionInput = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(sessionInput);
        final var input = DoVoteInput.builder()
                .sessionId(createdSession.getSessionId())
                .inFavor(TRUE)
                .cpf("ANY_CPF")
                .build();
        mvc.perform(post(VOTE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_PATH_TIMESTAMP).exists())
                .andExpect(jsonPath(JSON_PATH_PATH).value(VOTE_URL))
                .andExpect(jsonPath(JSON_PATH_MESSAGE).value("Session is not open."))
                .andExpect(jsonPath(JSON_PATH_CODE).value(BAD_REQUEST.value()));
    }

    @Test
    void shouldComputeVotes() throws Exception {
        final var sessionInput = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createdSession = this.createSession.execute(sessionInput);
        this.openSession.execute(createdSession.getSessionId());
        final var firstVote = DoVoteInput.builder()
                .sessionId(createdSession.getSessionId())
                .inFavor(TRUE)
                .cpf("ANY_CPF")
                .build();
        final var secondVote = DoVoteInput.builder()
                .sessionId(createdSession.getSessionId())
                .inFavor(TRUE)
                .cpf("SOME_CPF")
                .build();
        final var thirtyVote = DoVoteInput.builder()
                .sessionId(createdSession.getSessionId())
                .inFavor(FALSE)
                .cpf("ANOTHER_CPF")
                .build();
        this.doVote.execute(firstVote);
        this.doVote.execute(secondVote);
        this.doVote.execute(thirtyVote);
        mvc.perform(get(format("%s/compute/%d", VOTE_URL, createdSession.getSessionId()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_PATH_SESSION).value(createdSession.getSessionDescription()))
                .andExpect(jsonPath(JSON_PATH_IN_FAVOR_QUANTITY).value(2L))
                .andExpect(jsonPath(JSON_PATH_NOT_IN_FAVOR_QUANTITY).value(1L))
                .andExpect(jsonPath(JSON_PATH_TOTAL).value(3L));
    }
}
