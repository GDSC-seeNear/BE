package seeNear.seeNear_BE.domain.Chat.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.exception.CustomException;


import java.util.Map;

import static seeNear.seeNear_BE.exception.ErrorCode.*;
@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    private final TokenProvider tokenProvider;
    private final ElderlyRepository elderlyRepository;

    public HttpHandshakeInterceptor(TokenProvider tokenProvider, ElderlyRepository elderlyRepository) {
        this.tokenProvider = tokenProvider;
        this.elderlyRepository = elderlyRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws CustomException {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = tokenProvider.getToken(servletRequest.getServletRequest());

            try{
                if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

                    //Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                    Claims claims = tokenProvider.getPayload(token);
                    Role role = Enum.valueOf(Role.class, claims.get("role", String.class));
                    Integer userId = claims.get("id", Integer.class);
                    if (!role.equals(Role.ELDERLY)) {
                        throw new CustomException(INVALID_AUTHORITY, "role이 ELDERLY 인 사람만 요청 가능합니다.");
                    }

                    if (elderlyRepository.findById(userId) == null) {
                        throw new CustomException(MEMBER_NOT_FOUND, "존재하지 않는 유저입니다.");
                    }

                    attributes.put("elderlyId", userId);
                }
            }catch(Exception e){
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return false;
            }


        }


        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("afterHandshake");
    }
}
