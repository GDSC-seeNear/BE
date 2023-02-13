package seeNear.seeNear_BE.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.dto.*;

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
    public ResponseJwtTokenDto login(@RequestBody RequestCheckCodeDto loginInfo){
        return authService.login(loginInfo.getPhoneNumber(), loginInfo.getCertificationNumber());
    }
    @PostMapping(value="/signup")
    public ResponseJwtTokenDto signUp(@RequestBody RequestSignUpDto signInfo){
        return authService.signUp(signInfo.getName(),signInfo.getPhoneNumber());
    }

    @PostMapping(value="/send")
    public void sendSmsCode(@RequestBody RequestPhoneNumberDto phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        authService.sendSms(phoneNumber.getPhoneNumber());
    }
    @PostMapping(value="/check")
    public ResponseSignUpTokenDto checkSmsCode(@RequestBody RequestCheckCodeDto certifiInfo){
        return authService.checkSms(certifiInfo.getPhoneNumber(), certifiInfo.getCertificationNumber());
    }

    @GetMapping(value="/c")
    public String check(@RequestAttribute("member") Member member){
        return member.getName();
    }



}
