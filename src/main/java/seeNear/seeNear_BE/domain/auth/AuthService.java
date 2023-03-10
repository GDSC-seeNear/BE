package seeNear.seeNear_BE.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Guardian;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.auth.dto.ResponseSignUpTokenDto;
import seeNear.seeNear_BE.domain.auth.dto.ResponseJwtTokenDto;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.exception.CustomException;
import seeNear.seeNear_BE.global.sms.NaverSmsService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static seeNear.seeNear_BE.exception.ErrorCode.*;


@Service
@Transactional
public class AuthService {
    private final AuthRepository authRepository;
    private final NaverSmsService naverSmsService;
    private final ElderlyRepository elderlyRepository;
    private final GuardianRepository guardianRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthRepository authRepository,
                       NaverSmsService naverSmsService,
                       ElderlyRepository elderlyRepository, GuardianRepository guardianRepository, TokenProvider tokenProvider) {
        this.authRepository = authRepository;
        this.naverSmsService = naverSmsService;
        this.elderlyRepository = elderlyRepository;
        this.guardianRepository = guardianRepository;
        this.tokenProvider = tokenProvider;
    }

    public ResponseJwtTokenDto login(String phoneNumber, String certificationNumber, Role role){
        ResponseSignUpTokenDto validateCode = checkSms(phoneNumber,certificationNumber);
        if (!StringUtils.hasText(validateCode.getSignUpToken())){
            throw new CustomException(MISMATCH_CODE,certificationNumber);
        }

        Member member=null;
        if (role.equals(Role.Elderly)) {
            member = elderlyRepository.findByPhoneNumber(phoneNumber);
        } else if (role.equals(Role.GURDIAN)) {
            member = guardianRepository.findByPhoneNumber(phoneNumber);
        }

        if(member== null) {
            throw new CustomException(MEMBER_NOT_FOUND,phoneNumber);
        }
        return tokenProvider.createLoginToken(member.getId(),role);
    }

    public ResponseJwtTokenDto signUp(String name, String phoneNumber, Role role){
        Member member;
        Member saved=null;

        if (role.equals(Role.Elderly)) {
            member = elderlyRepository.findByPhoneNumber(phoneNumber);
            if(member!= null) {
                throw new CustomException(DUPLICATED_MEMBER,name+"/"+phoneNumber);
            }
            Elderly createdElderly = new Elderly(phoneNumber,name);
            saved = elderlyRepository.save(createdElderly);
        } else if (role.equals(Role.GURDIAN)) {
            member = guardianRepository.findByPhoneNumber(phoneNumber);
            if(member!= null) {
                throw new CustomException(DUPLICATED_MEMBER,name+"/"+phoneNumber);
            }
            Guardian createdGuardian = new Guardian(phoneNumber,name);
            saved = guardianRepository.save(createdGuardian);
        }

        return tokenProvider.createLoginToken(saved.getId(),role);
    }

    public ResponseSignUpTokenDto checkSms(String phoneNumber, String certificationNumber){
        String savedNum = authRepository.findCertificationNum(phoneNumber);
        if (StringUtils.hasText(savedNum)) {
            if (savedNum.equals(certificationNumber)) {
                //???????????? ????????????
                return tokenProvider.createSignUpToken(phoneNumber);
            }
        } else{
            //?????? ?????? ????????? ??????
            throw new CustomException(MISMATCH_INFO,phoneNumber+"/"+certificationNumber);
        }
        throw new CustomException(MISMATCH_CODE,certificationNumber);
    }

    public void sendSms(String phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String certificationNumber = naverSmsService.sendSms(phoneNumber);
        //?????? ?????? ?????? ????????? ?????? ?????? ??????
        authRepository.saveCertificationNum(phoneNumber,certificationNumber);
    }


}
