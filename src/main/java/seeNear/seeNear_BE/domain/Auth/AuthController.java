package seeNear.seeNear_BE.domain.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Auth.dto.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value="/refresh")
    public ResponseJwtTokenDto refresh(@RequestAttribute("member") Member member,@RequestAttribute("role") Role role){
        return authService.refresh(member.getId(),role);
    }

    @PostMapping(value="/elderly/login")
    public ResponseJwtTokenDto elderlyLogin(@RequestBody RequestCheckCodeDto loginInfo){
        return authService.login(loginInfo.getPhoneNumber(), loginInfo.getCertificationNumber(), Role.ELDERLY);
    }
    @PostMapping(value="/elderly/signup")
    public ResponseJwtTokenDto elderlySignUp(@RequestBody RequestSignUpDto signInfo){
        return authService.signUp(signInfo.getName(),signInfo.getPhoneNumber(),Role.ELDERLY);
    }
    @PostMapping(value="/guardian/login")
    public ResponseJwtTokenDto guardianLogin(@RequestBody RequestCheckCodeDto loginInfo){
        return authService.login(loginInfo.getPhoneNumber(), loginInfo.getCertificationNumber(), Role.GURDIAN);
    }
    @PostMapping(value="/guardian/signup")
    public ResponseJwtTokenDto guardianSignUp(@RequestBody RequestSignUpDto signInfo){
        return authService.signUp(signInfo.getName(),signInfo.getPhoneNumber(),Role.GURDIAN);
    }

    @PostMapping(value="/send")
    public ResponseEntity<String> sendSmsCode(@RequestBody RequestPhoneNumberDto phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        authService.sendSms(phoneNumber.getPhoneNumber());
        return ResponseEntity.ok().body("문자보내기가 성공했습니다");
    }
    @PostMapping(value="/check")
    public ResponseSignUpTokenDto checkSmsCode(@RequestBody RequestCheckCodeDto certifiInfo){
        return authService.checkSms(certifiInfo.getPhoneNumber(), certifiInfo.getCertificationNumber());
    }

}
