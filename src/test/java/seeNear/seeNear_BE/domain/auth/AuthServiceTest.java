package seeNear.seeNear_BE.domain.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.global.sms.NaverSmsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired private final AuthRepository authRepository;
    @Autowired private final NaverSmsService naverSmsService;
    @Autowired private final MemberRepository memberRepository;


    @Test
    void login() {
    }

    @Test
    void signUp() {
    }

    @Test
    void checkSms() {
    }

    @Test
    void sendSms() {
    }

    @Test
    void createJwtToken() {
    }

    @Test
    void validateToken() {
    }

    @Test
    void getPayload() {
    }
}