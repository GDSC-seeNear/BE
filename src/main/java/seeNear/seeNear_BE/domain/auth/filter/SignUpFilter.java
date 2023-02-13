package seeNear.seeNear_BE.domain.auth.filter;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.TokenProvider;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_TOKEN_INFO;
import static seeNear.seeNear_BE.exception.ErrorCode.MEMBER_NOT_FOUND;

@WebFilter(urlPatterns= "/auth/signup")
public class SignUpFilter extends JwtFilter{
    AuthRepository authRepository;

    public SignUpFilter(MemberRepository memberRepository, TokenProvider tokenProvider, AuthRepository authRepository) {
        super(memberRepository, tokenProvider);
        this.authRepository = authRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = super.getToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Claims payload = tokenProvider.getPayload(jwt);
            String phoneNumber = payload.get("phoneNumber").toString();
            //에러 걸어주기

            if (phoneNumber != null) {
                System.out.println(phoneNumber);
                System.out.println(payload.get("phoneNumber").toString());
                //보낸 정보랑 맞는지 확인해야댐
                request.setAttribute("phoneNumber",phoneNumber);
            }else{
                System.out.println(phoneNumber);
                System.out.println(payload.get("phoneNumber").toString());
                System.out.println((Objects.equals(payload.get("phoneNumber").toString(), phoneNumber)));
                throw new CustomException(INVALID_TOKEN_INFO,jwt);
            }

        }

        filterChain.doFilter(request, response);
    }
}
