package seeNear.seeNear_BE.global.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class NaverSmsServiceTest {

    private final NaverSmsService naver = new NaverSmsService("ncp:sms:kr:301251589425:seenear",
    "M88gHwLp9fQ2YUYJ1psr", "257pUtSzypBaiZ52xFfDnayhPRJmBvOU4FEVQMWH","01082834069");

    @Test
    void sendSms() throws IOException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException {
        var response = naver.sendSms("01082834069");
        System.out.println(response);
    }

    @Test
    void makeSignature() throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long time = System.currentTimeMillis();
        String sig = naver.makeSignature(time);
        System.out.println(sig);
    }
}