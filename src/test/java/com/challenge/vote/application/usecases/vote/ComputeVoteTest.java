package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.OpenSession;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import com.challenge.vote.infra.repositories.memories.VoteRepositoryMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@SpringBootTest
public class ComputeVoteTest {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @Autowired
    private VoteRepositoryMemory voteRepositoryMemory;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
        this.voteRepositoryMemory.clean();
    }

    @Test
    void ShouldComputeVotes() throws Exception {
        final var createSession = new CreateSession(sessionRepository);
        final var openSession = new OpenSession(sessionRepository);
        final var doVote = new DoVote(sessionRepository, voteRepositoryMemory);
        final var computeVote = new ComputeVote(voteRepositoryMemory, sessionRepository);
        final var sessionInput = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var firstInputVote = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .idSession(sessionInput.getSessionId())
                .inFavor(TRUE)
                .build();
        final var secondInputVote = DoVoteInput.builder()
                .cpf("ANOTHER_CPF")
                .id(2L)
                .idSession(sessionInput.getSessionId())
                .inFavor(FALSE)
                .build();
        final var thirdVoteInput = DoVoteInput.builder()
                .cpf("SOME_CPF")
                .id(3L)
                .idSession(sessionInput.getSessionId())
                .inFavor(TRUE)
                .build();
        final var session = createSession.execute(sessionInput);
        openSession.execute(session.getSessionId());
        doVote.execute(firstInputVote);
        doVote.execute(secondInputVote);
        doVote.execute(thirdVoteInput);
        final var output = computeVote.execute(sessionInput.getSessionId());
        Assertions.assertEquals(output.getSession(), "ANY_SESSION");
        Assertions.assertEquals(output.getInFavorQuantity(), 2L);
        Assertions.assertEquals(output.getNotInFavorQuantity(), 1L);
        Assertions.assertEquals(output.getInFavor(), Boolean.TRUE);
    }
}
