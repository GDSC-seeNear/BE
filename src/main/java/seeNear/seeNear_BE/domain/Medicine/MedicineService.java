package seeNear.seeNear_BE.domain.Medicine;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestCreateMedicineDto;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestUpdateMedicineDto;

import seeNear.seeNear_BE.exception.CustomException;

import java.util.List;
import static seeNear.seeNear_BE.exception.ErrorCode.MISMATCH_INFO;

@Service
public class MedicineService {
    ModelMapper modelMapper = new ModelMapper();
    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine createMedicine(RequestCreateMedicineDto medicine) {
        Medicine newMedicine = new Medicine(medicine.getName(), medicine.getElderlyId(), medicine.getPeriod());

        return medicineRepository.save(newMedicine);
    }

    public Medicine updateMedicine(RequestUpdateMedicineDto newMedicine, int id) {
        //path의 id와 body의 id가 일치하는지 확인
        if (newMedicine.getId() != id) {
            throw new CustomException(MISMATCH_INFO, String.format("path id: %d  body id: %d", id, newMedicine.getId()));
        }

        return medicineRepository.update(modelMapper.map(newMedicine, Medicine.class));
    }

    public void deleteMedicine(int id) {
        Medicine medicine = medicineRepository.findById(id);

        medicineRepository.delete(medicine);
    }

    public List<Medicine> findByElderlyId(int elderlyId) {

        return medicineRepository.findByElderlyId(elderlyId);
    }
}
