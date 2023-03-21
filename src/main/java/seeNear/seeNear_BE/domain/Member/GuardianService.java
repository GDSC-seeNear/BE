package seeNear.seeNear_BE.domain.Member;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;
import seeNear.seeNear_BE.domain.Auth.AuthService;
import seeNear.seeNear_BE.exception.CustomException;

import java.util.List;

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

    public Elderly getOneManagedElderly(int elderlyId,int guardianId) {
        Elderly elderly = elderlyRepository.findById(elderlyId);

        if (elderly == null) {
            throw new CustomException(MEMBER_NOT_FOUND, String.format("request elderly id: %d", elderlyId));
        }
        if (!elderly.getIsConnect() || elderly.getGuardianId() != guardianId) {
            throw new CustomException(INVALID_AUTHORITY, String.format("user id: %d 와 elderly의 guardianId 이 같지 않습니다", guardianId));
        }
        return elderly;
    }


    public List<Elderly> getManagedElderly(int guardianId) {
        return elderlyRepository.findByGuardianId(guardianId);
    }

    public Elderly resisterElderly(RequestElderly updateInfo, int guardianId) {
        if (guardianId != updateInfo.getGuardianId()) {
            throw new CustomException(INVALID_AUTHORITY,
                    String.format("user value: %d  request value: %d", guardianId,updateInfo.getGuardianId()));
        }
        if (elderlyRepository.findById(updateInfo.getId()) == null) {
            throw new CustomException(MEMBER_NOT_FOUND, String.format("request elderly id: %d", updateInfo.getId()));
        }

        Elderly elderly = modelMapper.map(updateInfo, Elderly.class);
        return elderlyRepository.update(elderly);
    }

    public Elderly checkResister(String phoneNumber, String certificationNumber) {
        authService.checkSms(phoneNumber,certificationNumber);
        Elderly elderly = elderlyRepository.findByPhoneNumber(phoneNumber);
        if(elderly== null) {
            throw new CustomException(MEMBER_NOT_FOUND,phoneNumber);
        }
        return elderly;
    }

}
