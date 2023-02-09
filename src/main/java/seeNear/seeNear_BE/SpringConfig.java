package seeNear.seeNear_BE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seeNear.seeNear_BE.domain.Member.MemberService;
import seeNear.seeNear_BE.domain.Member.MemoryMemberRepository;
import seeNear.seeNear_BE.domain.auth.AuthService;
import seeNear.seeNear_BE.domain.auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }



}
