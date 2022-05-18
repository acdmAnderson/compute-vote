package com.challenge.vote.domain.services;

import com.challenge.vote.domain.integrations.member.MemberIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.challenge.vote.domain.integrations.member.MemberStatusEnum.ABLE_TO_VOTE;

@Service
public class MemberValidator {
    private final MemberIntegration memberIntegration;

    @Autowired
    public MemberValidator(MemberIntegration memberIntegration) {
        this.memberIntegration = memberIntegration;
    }

    public Boolean isAble(String cpf) {
        final var result = memberIntegration.verifyMember(cpf);
        return ABLE_TO_VOTE.equals(result.getStatus());
    }
}
