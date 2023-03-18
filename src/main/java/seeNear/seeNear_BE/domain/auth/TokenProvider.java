package seeNear.seeNear_BE.domain.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.auth.dto.ResponseSignUpTokenDto;
import seeNear.seeNear_BE.domain.auth.dto.ResponseJwtTokenDto;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.exception.CustomException;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_TOKEN;
@Service
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private Key secret;
    @Value("${jwt.secret}")
    public String stringSecret;
    @Value("${jwt.access_expires_in}")
    private int accessExpired;
    @Value("${jwt.refresh_expires_in}")
    private int refreshExpired;
    private final AuthRepository authRepository;

    @Autowired
    public TokenProvider(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(stringSecret);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
    }

    public ResponseSignUpTokenDto createSignUpToken(String phoneNumber){
        UUID uuid = UUID.randomUUID();
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Date signUpExpiration = Date.from(issuedAt.plus(accessExpired, ChronoUnit.HOURS));

        String signUpToken= Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("seeNear") // (2)
                .setIssuedAt(new Date()) // (3)
                .setExpiration(signUpExpiration) // (4)
                .claim("phoneNumber", phoneNumber) // (5)
                .claim("uuid", uuid.toString())
                .signWith(secret, SignatureAlgorithm.HS256) // (6)
                .compact();


        return new ResponseSignUpTokenDto(signUpToken);
    }

    public ResponseJwtTokenDto createLoginToken(int memberId, Role role) {
        UUID uuid = UUID.randomUUID();
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Date accessExpiration = Date.from(issuedAt.plus(accessExpired, ChronoUnit.HOURS));
        Date refreshExpiration = Date.from(issuedAt.plus(refreshExpired, ChronoUnit.HOURS));

        var accessToken= Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("seeNear") // (2)
                .setIssuedAt(new Date()) // (3)
                .setExpiration(accessExpiration) // (4)
                .claim("id", memberId) // (5)
                .claim("uuid", uuid.toString())
                .claim("role", role.toString())
                .signWith(secret, SignatureAlgorithm.HS256) // (6)
                .compact();
        var refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("seeNear") // (2)
                .setIssuedAt(new Date()) // (3)
                .setExpiration(refreshExpiration) // (4)
                .claim("id", memberId) // (5)
                .claim("uuid", uuid.toString())
                .claim("role", role.toString())
                .signWith(secret,SignatureAlgorithm.HS256) // (6)
                .compact();

        authRepository.saveToken(uuid.toString(),refreshToken);
        return new ResponseJwtTokenDto(accessToken,refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        throw new CustomException(INVALID_TOKEN,token);
    }

    public Claims getPayload(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
