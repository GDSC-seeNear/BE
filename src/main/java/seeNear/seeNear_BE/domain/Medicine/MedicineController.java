package seeNear.seeNear_BE.domain.Medicine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestCreateMedicineDto;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestUpdateMedicineDto;
import seeNear.seeNear_BE.domain.Member.domain.Member;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }
    @PostMapping(value = "/create")
    public List<Medicine> createMedicines(@RequestBody List<RequestCreateMedicineDto> medicines,@RequestAttribute("member") Member member, @RequestAttribute("role") String role) {
        return medicineService.createMedicines(medicines,member.getId(),role);
    }
    @PatchMapping(value = "/update/{id}")
    public Medicine updateMedicine(@PathVariable int id,@RequestBody RequestUpdateMedicineDto medicine, @RequestAttribute("member") Member member, @RequestAttribute("role") String role) {
        return medicineService.updateMedicine(medicine,id,member.getId(),role);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable int id, @RequestAttribute("member") Member member, @RequestAttribute("role") String role) {
        medicineService.deleteMedicine(id,member.getId(),role);
        return ResponseEntity.ok().body("삭제가 정상적으로 진행 되었습니다.");
    }
    @GetMapping(value = "/{elderlyId}")
    public List<Medicine> findByElderlyId(@PathVariable int elderlyId, @RequestAttribute("member") Member member, @RequestAttribute("role") String role) {
        return medicineService.findByElderlyId(elderlyId,member.getId(),role);
    }


}
