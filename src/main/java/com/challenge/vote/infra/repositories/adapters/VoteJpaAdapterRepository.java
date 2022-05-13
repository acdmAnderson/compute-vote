package com.challenge.vote.infra.repositories.adapters;

import com.challenge.vote.domain.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteJpaAdapterRepository extends JpaRepository<Vote, Long> {
    List<Vote> findBySessionId(Long id);

    Vote findBySessionIdAndCpf(Long id, String cpf);
}
