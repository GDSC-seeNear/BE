package seeNear.seeNear_BE.domain.Medicine;

import org.modelmapper.ModelMapper;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestCreateMedicineDto;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestUpdateMedicineDto;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.exception.CustomException;

import java.util.List;
import java.util.stream.Collectors;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_AUTHORITY;
import static seeNear.seeNear_BE.exception.ErrorCode.MISMATCH_INFO;

@Service
public class MedicineService {
    ModelMapper modelMapper = new ModelMapper();
    private final MedicineRepository medicineRepository;
    private final ElderlyRepository elderlyRepository;
    public MedicineService(MedicineRepository medicineRepository, ElderlyRepository elderlyRepository) {
        this.medicineRepository = medicineRepository;
        this.elderlyRepository = elderlyRepository;
    }

    private boolean isPossibleCRUD(int elderlyId,int memberId, String role){
        //body의 elderly id와 member id가 일치하는지 확인
        if(role.equals(Role.ELDERLY.toString()) && memberId != elderlyId) {
            throw new CustomException(INVALID_AUTHORITY,String.format("memberId : %d와 elderlyId: %d가 일치하지 않습니다.",memberId,elderlyId));
        }
        //body의 elderly id로 유저 찾아서 연결된 guardian id와 member id가 일치하는지 확인
        if (role.equals(Role.GURDIAN.toString())) {
            Elderly gurdianInChargeOfElderly = elderlyRepository.findById(elderlyId);
            if (!gurdianInChargeOfElderly.getIsConnect()){
                throw new CustomException(INVALID_AUTHORITY,String.format("member %d 와 연결된 guardian이 없습니다",elderlyId));
            } else if (gurdianInChargeOfElderly.getGuardianId() != memberId) {
                throw new CustomException(INVALID_AUTHORITY, String.format("memberId : %d와 연결된 guardianId: %d가 일치하지 않습니다.",elderlyId, memberId));
            }
        }
        return true;

    }
    public List<Medicine> createMedicines(List<RequestCreateMedicineDto> medicines,int memberId, String role) {
        return medicines.stream().map(
                medicine -> {
                    Medicine newMedicine = new Medicine(medicine.getName(), medicine.getElderlyId(), medicine.getPeriod());
                    if (!isPossibleCRUD(newMedicine.getElderlyId(),memberId,role)) {
                        throw new CustomException(INVALID_AUTHORITY, String.format("생성 권한이 없습니다."));
                    }
                    return medicineRepository.save(newMedicine);
                }
            ).collect(Collectors.toList());
    }

    public Medicine updateMedicine(RequestUpdateMedicineDto newMedicine, int id,int memberId, String role) {
        //path의 id와 body의 id가 일치하는지 확인
        if (newMedicine.getId() != id) {
            throw new CustomException(MISMATCH_INFO, String.format("path id: %d  body id: %d", id, newMedicine.getId()));
        }
        //path의 id로 newMedicine 찾아서 존재하는지 확인
        Medicine medicine = medicineRepository.findById(id);
        if (medicine == null) {
            throw new CustomException(MISMATCH_INFO, String.format("newMedicine id : %d 가 존재하지 않습니다.", id));
        }
        if (isPossibleCRUD(newMedicine.getElderlyId(), memberId, role)) {
            return medicineRepository.update(modelMapper.map(newMedicine, Medicine.class));
        }
        throw new CustomException(INVALID_AUTHORITY, String.format("수정권한이 없습니다."));
    }

    public void deleteMedicine(int id,int memberId, String role) {
        Medicine medicine = medicineRepository.findById(id);
        //path의 id로 medicine 찾아서 존재하는지 확인
        if (medicineRepository.findById(id) == null) {
            throw new CustomException(MISMATCH_INFO, String.format("medicine id : %d 가 존재하지 않습니다.", id));
        }
        if (isPossibleCRUD(medicine.getElderlyId(),memberId,role)) {
            medicineRepository.delete(medicine);
            return;
        }
        throw new CustomException(INVALID_AUTHORITY, String.format("삭제 권한이 없습니다."));
    }

    public List<Medicine> findByElderlyId(int elderlyId,int memberId, String role) {
        if (isPossibleCRUD(elderlyId,memberId,role)) {
            return medicineRepository.findByElderlyId(elderlyId);
        }
        throw new CustomException(INVALID_AUTHORITY, String.format("조회 권한이 없습니다."));
    }
}
