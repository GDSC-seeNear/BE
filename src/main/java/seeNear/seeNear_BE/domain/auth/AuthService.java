package seeNear.seeNear_BE.domain.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.dto.ResponseSignUpTokenDto;
import seeNear.seeNear_BE.domain.auth.dto.ResponseJwtTokenDto;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.exception.CustomException;
import seeNear.seeNear_BE.global.sms.NaverSmsService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static seeNear.seeNear_BE.exception.ErrorCode.*;


@Service
@Transactional
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthRepository authRepository;
    private final NaverSmsService naverSmsService;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthRepository authRepository,
                       NaverSmsService naverSmsService,
                       MemberRepository memberRepository,
                       TokenProvider tokenProvider) {
        this.authRepository = authRepository;
        this.naverSmsService = naverSmsService;
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public ResponseJwtTokenDto login(String phoneNumber, String certificationNumber){
        ResponseSignUpTokenDto validateCode = checkSms(phoneNumber,certificationNumber);
        if (!StringUtils.hasText(validateCode.getSignUpToken())){
            throw new CustomException(MISMATCH_CODE,certificationNumber);
        }
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if(member== null) {
            throw new CustomException(MEMBER_NOT_FOUND,phoneNumber);
        }
        return tokenProvider.createLoginToken(member.getId());
    }

    public ResponseJwtTokenDto signUp(String name, String phoneNumber){
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if(member!= null) {
            throw new CustomException(DUPLICATED_MEMBER,name+"/"+phoneNumber);
        }
        Member savedMember = new Member(new Random().nextLong(),name,phoneNumber);
        memberRepository.save(savedMember);
        return tokenProvider.createLoginToken(savedMember.getId());
    }

    public ResponseSignUpTokenDto checkSms(String phoneNumber, String certificationNumber){
        String savedNum = authRepository.findCertificationNum(phoneNumber);
        if (StringUtils.hasText(savedNum)) {
            if (savedNum.equals(certificationNumber)) {
                //인증번호 삭제로직
                return tokenProvider.createSignUpToken(phoneNumber);
            }
        } else{
            throw new CustomException(MISMATCH_INFO,phoneNumber+"/"+certificationNumber);
        }
        throw new CustomException(MISMATCH_CODE,certificationNumber);
    }

    public void sendSms(String phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String certificationNumber = naverSmsService.sendSms(phoneNumber);
        authRepository.saveCertificationNum(phoneNumber,certificationNumber);
    }


}
