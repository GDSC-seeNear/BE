package seeNear.seeNear_BE.domain.Auth.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static seeNear.seeNear_BE.exception.ErrorCode.*;
@Slf4j
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
                throw new CustomException(MISMATCH_INFO,"refreshToken is not exist");
            }

            Member member = null;
            var role = payload.get("role",String.class);
            Role memberRole = Enum.valueOf(Role.class, role);

            if (memberRole.equals(Role.ELDERLY)) {
                member = elderlyRepository.findById(Integer.parseInt(payload.get("id").toString()));
            }else if (memberRole.equals(Role.GURDIAN)) {
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
