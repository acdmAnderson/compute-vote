package com.challenge.vote.domain.services;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.infra.integrations.member.MemberIntegrationFeign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberValidatorTest implements VoteApplicationTests {

    @Autowired
    private MemberIntegrationFeign memberIntegration;

    @Test
    void shouldVerifyMember() {
        final var memberValidator = new MemberValidator(this.memberIntegration);
        Assertions.assertInstanceOf(Boolean.class, memberValidator.isAble("12559757680"));
    }
}
