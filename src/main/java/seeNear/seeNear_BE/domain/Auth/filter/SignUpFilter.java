package seeNear.seeNear_BE.domain.Auth.filter;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;

import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.domain.Auth.Interface.AuthRepository;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_TOKEN_INFO;

@WebFilter(urlPatterns= {"/auth/elderly/signup","/auth/guardian/signup"})
public class SignUpFilter extends JwtFilter{
    AuthRepository authRepository;

    public SignUpFilter(seeNear.seeNear_BE.domain.Member.ElderlyRepository elderlyRepository, GuardianRepository guardianRepository, TokenProvider tokenProvider, AuthRepository authRepository) {
        super(elderlyRepository, guardianRepository,tokenProvider);
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
                //보낸 정보랑 맞는지 확인해야댐
                request.setAttribute("phoneNumber",phoneNumber);
            }else{
                throw new CustomException(INVALID_TOKEN_INFO,jwt);
            }

        }

        filterChain.doFilter(request, response);
    }
}
