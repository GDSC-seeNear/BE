package seeNear.seeNear_BE.domain.Auth.filter;


import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static seeNear.seeNear_BE.exception.ErrorCode.*;

//@WebFilter(urlPatterns= {"/guardian/*","/elderly/*","/medicine/*"})
public class JwtFilter extends OncePerRequestFilter {

    final ElderlyRepository elderlyRepository;
    final GuardianRepository guardianRepository;
    final TokenProvider tokenProvider;

    public JwtFilter(ElderlyRepository elderlyRepository, GuardianRepository guardianRepository, TokenProvider tokenProvider) {
        this.elderlyRepository = elderlyRepository;
        this.guardianRepository = guardianRepository;
        this.tokenProvider = tokenProvider;
    }

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = tokenProvider.getToken(request);


        // 2. validateToken 으로 토큰 유효성 검사
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Claims payload = tokenProvider.getPayload(jwt);

            Member member = null;
            System.out.println("member");
            var role = payload.get("role",String.class);
            Role memberRole = Enum.valueOf(Role.class, role);


            if (memberRole.equals(Role.ELDERLY)) {
                member = elderlyRepository.findById((int)payload.get("id"));
            }else if (memberRole.equals(Role.GURDIAN)) {
                member = guardianRepository.findById((int)payload.get("id"));
            }

            if (member == null) {
                throw new CustomException(MEMBER_NOT_FOUND,jwt);
            }else{
                request.setAttribute("member",member);
                request.setAttribute("role",memberRole);
            }

        }

        filterChain.doFilter(request, response);
    }

}
