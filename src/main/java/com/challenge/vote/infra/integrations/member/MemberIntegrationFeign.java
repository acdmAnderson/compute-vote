package com.challenge.vote.infra.integrations.member;

import com.challenge.vote.domain.integrations.member.MemberIntegration;
import com.challenge.vote.domain.integrations.member.MemberIntegrationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Profile("!test")
@FeignClient(name = "MemberIntegration", url = "https://user-info.herokuapp.com/users")
public interface MemberIntegrationFeign extends MemberIntegration {

    @GetMapping("/{cpf}")
    MemberIntegrationResponse verifyMember(@PathVariable("cpf") String cpf);
}
