package seeNear.seeNear_BE.domain.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import seeNear.seeNear_BE.domain.Member.MemoryMemberRepository;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.dto.responseJwtTokenDto;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.exception.ForbiddenException;
import seeNear.seeNear_BE.global.sms.NaverSmsService;
import seeNear.seeNear_BE.global.sms.dto.CertificationInfo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@Transactional
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final Key secret;
    private final int accessExpired;
    private final int refreshExpired;
    private final AuthRepository authRepository;
    private final NaverSmsService naverSmsService;
    private final MemberRepository memberRepository;

    @Autowired
    public AuthService(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access_expires_in}") int accessExpired,
                       @Value("${jwt.refresh_expires_in}") int refreshExpired,
                       AuthRepository authRepository,
                       NaverSmsService naverSmsService,
                       MemberRepository memberRepository) {
        this.authRepository = authRepository;
        this.naverSmsService = naverSmsService;
        this.memberRepository = memberRepository;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        this.secret = key;
        this.accessExpired = accessExpired;
        this.refreshExpired = refreshExpired;
    }

    public responseJwtTokenDto login(String phoneNumber,String requestId, String certificationNumber){
        boolean validateCode = checkSms(requestId,certificationNumber);
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if(validateCode && (member!= null)) {
            return createJwtToken(member.getId());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public responseJwtTokenDto signUp(String name, String phoneNumber){
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if(member!= null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Member savedMember = new Member(new Random().nextLong(),name,phoneNumber);
        return createJwtToken(savedMember.getId());
    }

    public boolean checkSms(String requestId, String certificationNumber){
        String savedNum = authRepository.findCertificationNum(requestId);
        if (StringUtils.hasText(savedNum)) {
            if (savedNum.equals(certificationNumber)) {
                return true;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public CertificationInfo sendSms(String phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        CertificationInfo sendResult = naverSmsService.sendSms(phoneNumber);
        authRepository.saveCertificationNum(sendResult.getRequestId(),sendResult.getCertificationNumber());
        return sendResult;
    }

    public responseJwtTokenDto createJwtToken(Long memberId) {
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
                .signWith(secret,SignatureAlgorithm.HS256) // (6)
                .compact();
        var refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("seeNear") // (2)
                .setIssuedAt(new Date()) // (3)
                .setExpiration(refreshExpiration) // (4)
                .claim("id", memberId) // (5)
                .claim("uuid", uuid.toString())
                .signWith(secret,SignatureAlgorithm.HS256) // (6)
                .compact();


        return new responseJwtTokenDto(accessToken,refreshToken);
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
        return false;
    }

    public Claims getPayload(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
