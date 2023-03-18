package seeNear.seeNear_BE.domain.auth.filter;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Guardian;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.auth.TokenProvider;
import seeNear.seeNear_BE.domain.auth.dto.ResponseJwtTokenDto;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import static seeNear.seeNear_BE.exception.ErrorCode.*;

@WebFilter(urlPatterns= {"/auth/refresh"})
public class RefreshFilter extends JwtFilter{
    private final MemoryAuthRepository memoryAuthRepository;
    public RefreshFilter(ElderlyRepository elderlyRepository, GuardianRepository guardianRepository, TokenProvider tokenProvider,MemoryAuthRepository memoryAuthRepository) {
        super(elderlyRepository, guardianRepository, tokenProvider);
        this.memoryAuthRepository=memoryAuthRepository;
    }

//    private void setResponseHeader(HttpServletResponse response, ResponseJwtTokenDto jwt) throws IOException {
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(jwt);
//
//        PrintWriter out = response.getWriter();
//        out.println(jsonString);
//        out.close();
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = super.getToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Claims payload = tokenProvider.getPayload(jwt);
            String uuid = payload.get("uuid", String.class);

            if (memoryAuthRepository.findToken(uuid) == null) {
                throw new CustomException(MISMATCH_INFO,"refreshToken이 존재하지 않습니다");
            }

            Member member = null;
            var memberRole = payload.get("role",String.class);

            if (memberRole.equals(Role.ELDERLY.toString())) {
                member = elderlyRepository.findById(Integer.parseInt(payload.get("id").toString()));
            }else if (memberRole.equals(Role.GURDIAN.toString())) {
                member = guardianRepository.findById(Integer.parseInt(payload.get("id").toString()));
            }else {
                throw new CustomException(INVALID_TOKEN_INFO, "토큰 정보가 올바르지 않습니다");
            }

            if (member == null) {
                throw new CustomException(MEMBER_NOT_FOUND,jwt);
            } else{
                request.setAttribute("member",member);
                request.setAttribute("role",memberRole);
            }

        }

        filterChain.doFilter(request, response);
    }
}
