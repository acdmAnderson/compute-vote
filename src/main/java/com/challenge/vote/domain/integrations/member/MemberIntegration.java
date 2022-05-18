package com.challenge.vote.domain.integrations.member;

public interface MemberIntegration {
    MemberIntegrationResponse verifyMember(String cpf);
}
