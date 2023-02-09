package seeNear.seeNear_BE.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.auth.dto.RequestLoginDto;
import seeNear.seeNear_BE.domain.auth.dto.RequestPhoneNumberDto;
import seeNear.seeNear_BE.domain.auth.dto.RequestSignUpDto;
import seeNear.seeNear_BE.domain.auth.dto.responseJwtTokenDto;
import seeNear.seeNear_BE.global.sms.dto.CertificationInfo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value="/login")
    public responseJwtTokenDto login(@RequestBody RequestLoginDto loginInfo){
        return authService.login(loginInfo.getPhoneNumber(),loginInfo.getRequestId(), loginInfo.getCertificationNumber());
    }
    @PostMapping(value="/signup")
    public responseJwtTokenDto signUp(@RequestBody RequestSignUpDto signInfo){
        return authService.signUp(signInfo.getName(),signInfo.getPhoneNumber());
    }

    @PostMapping(value="/send")
    public String sendSmsCode(@RequestBody RequestPhoneNumberDto phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        CertificationInfo result = authService.sendSms(phoneNumber.getPhoneNumber());
        return result.getRequestId();
    }
    @PostMapping(value="/check")
    public boolean checkSmsCode(@RequestBody CertificationInfo certifiInfo){
        return authService.checkSms(certifiInfo.getRequestId(),certifiInfo.getCertificationNumber());
    }



}
