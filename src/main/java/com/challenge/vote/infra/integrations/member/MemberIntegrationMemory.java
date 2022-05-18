package com.challenge.vote.infra.integrations.member;

import com.challenge.vote.domain.integrations.member.MemberIntegration;
import com.challenge.vote.domain.integrations.member.MemberIntegrationResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.challenge.vote.domain.integrations.member.MemberStatusEnum.ABLE_TO_VOTE;

@Component
@Profile("test")
public class MemberIntegrationMemory implements MemberIntegration {

    @Override
    public MemberIntegrationResponse verifyMember(String cpf) {
        return MemberIntegrationResponse.builder()
                .status(ABLE_TO_VOTE)
                .build();
    }
}
