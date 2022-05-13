package com.challenge.vote.infra.repositories.adapters;

import com.challenge.vote.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionJPAAdapterRepository extends JpaRepository<Session, Long> {
}
