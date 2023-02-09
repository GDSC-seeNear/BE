package seeNear.seeNear_BE.service;

import org.junit.jupiter.api.Test;
import seeNear.seeNear_BE.domain.Member.MemoryMemberRepository;
import seeNear.seeNear_BE.domain.auth.AuthService;
import seeNear.seeNear_BE.domain.auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.auth.dto.responseJwtTokenDto;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.global.sms.NaverSmsService;

public class AuthServiceTest {

    private AuthRepository authRepository = new MemoryAuthRepository();
    private final NaverSmsService naver = new NaverSmsService("ncp:sms:kr:301251589425:seenear",
            "M88gHwLp9fQ2YUYJ1psr", "257pUtSzypBaiZ52xFfDnayhPRJmBvOU4FEVQMWH","01082834069");
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private AuthService authService = new AuthService("rlawnghsdfsdfsefwefadcsdcsdcsdvcsdcsdcsfvdfbvdfvadfvadfvadfvdfvdsfvdfvsdaewrwdfcsadks",
            1,48,authRepository,naver,memberRepository);


    @Test
    public void createJwtToken(){
        var token = authService.createJwtToken(1L);
        System.out.println(token.getAccessToken());
    }

    @Test
    public void validateToken(){
        var token = authService.createJwtToken(1L);
        var t= authService.validateToken(token.getAccessToken());
        var f = authService.validateToken("sjkdvbsiodjcm");
        System.out.println(t);
        System.out.println(f);
    }

    @Test
    public void getPayload(){
        var token = authService.createJwtToken(1L);
        var payload = authService.getPayload(token.getAccessToken());
        System.out.println(payload.get("id"));
    }

    @Test
    public void sendSms(){

    }
}
