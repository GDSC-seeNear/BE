package seeNear.seeNear_BE.domain.Member;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;
import seeNear.seeNear_BE.domain.auth.AuthService;
import seeNear.seeNear_BE.domain.auth.dto.ResponseSignUpTokenDto;
import seeNear.seeNear_BE.exception.CustomException;

import static seeNear.seeNear_BE.exception.ErrorCode.*;

@Service
public class GuardianService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ElderlyRepository elderlyRepository;
    private final GuardianRepository guardianRepository;
    private final AuthService authService;
    @Autowired
    public GuardianService(ElderlyRepository elderlyRepository, GuardianRepository guardianRepository, AuthService authService){
        this.elderlyRepository = elderlyRepository;
        this.guardianRepository = guardianRepository;
        this.authService =  authService;

    }

    public Elderly resisterElderly(RequestElderly updateInfo, int guardianId) {
        if (guardianId != updateInfo.getGuardianId()) {
            throw new CustomException(INVALID_TOKEN_INFO,
                    String.format("token_value: %d  request_value: %d", guardianId,updateInfo.getGuardianId()));
        }

        Elderly elderly = modelMapper.map(updateInfo, Elderly.class);
        return elderlyRepository.update(elderly);
    }

    public Elderly checkResister(String phoneNumber, String certificationNumber) {
        ResponseSignUpTokenDto validateCode = authService.checkSms(phoneNumber,certificationNumber);
        if (!StringUtils.hasText(validateCode.getSignUpToken())){
            throw new CustomException(MISMATCH_CODE,certificationNumber);
        }
        Elderly elderly = elderlyRepository.findByPhoneNumber(phoneNumber);

        if(elderly== null) {
            throw new CustomException(MEMBER_NOT_FOUND,phoneNumber);
        }
        return elderly;
    }

}
