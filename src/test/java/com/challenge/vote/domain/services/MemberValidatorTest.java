package com.challenge.vote.domain.services;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.infra.integrations.member.MemberIntegrationFeign;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberValidatorTest implements VoteApplicationTests {

    @Autowired
    private MemberIntegrationFeign memberIntegration;

    @Test
    void shouldVerifyMember() {
        final var memberValidator = new MemberValidator(this.memberIntegration);
        assertInstanceOf(Boolean.class, memberValidator.isAble("12559757680"));
    }

    @Test
    void shouldThrow_whenCpfIsInvalid() {
        final var memberValidator = new MemberValidator(this.memberIntegration);
        assertThrows(FeignException.NotFound.class, () -> memberValidator.isAble("WRONG_CPF"));
    }
}
