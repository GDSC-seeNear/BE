package seeNear.seeNear_BE;

import io.jsonwebtoken.Jwt;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seeNear.seeNear_BE.domain.Member.MemberService;
import seeNear.seeNear_BE.domain.Member.MemoryMemberRepository;
import seeNear.seeNear_BE.domain.auth.AuthService;
import seeNear.seeNear_BE.domain.auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.auth.TokenProvider;
import seeNear.seeNear_BE.domain.auth.filter.JwtFilter;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.exception.GlobalExceptionHandlerFilter;

import java.util.Arrays;

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


    @Bean
    public FilterRegistrationBean<GlobalExceptionHandlerFilter> GlobalExceptionHandlerFilter(){
        FilterRegistrationBean<GlobalExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GlobalExceptionHandlerFilter());
        registrationBean.addUrlPatterns("/*");
        //registrationBean.setOrder(1);
        registrationBean.setName("first-filter");
        return registrationBean;
    }




}
