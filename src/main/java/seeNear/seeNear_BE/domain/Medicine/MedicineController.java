package seeNear.seeNear_BE.domain.Medicine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestCreateMedicineDto;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestUpdateMedicineDto;
import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }
    @PostMapping(value = "/create")
    public Medicine createMedicines(@RequestBody RequestCreateMedicineDto medicine) {
        return medicineService.createMedicine(medicine);
    }
    @PatchMapping(value = "/update/{id}")
    public Medicine updateMedicine(@PathVariable int id,@RequestBody RequestUpdateMedicineDto medicine) {
        return medicineService.updateMedicine(medicine,id);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable int id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok().body("삭제가 정상적으로 진행 되었습니다.");
    }
    @GetMapping(value = "/getByElderlyId/{elderlyId}")
    public List<Medicine> findByElderlyId(@PathVariable int elderlyId) {
        return medicineService.findByElderlyId(elderlyId);
    }


}
