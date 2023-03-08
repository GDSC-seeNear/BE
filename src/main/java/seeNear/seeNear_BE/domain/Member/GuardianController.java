package seeNear.seeNear_BE.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;
import seeNear.seeNear_BE.domain.auth.dto.RequestCheckCodeDto;

@RestController
@RequestMapping("/guardian")
public class GuardianController {

    private GuardianService guardianService;
    @Autowired
    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping(value = "/checkResister")
    public Elderly checkResister (@RequestBody RequestCheckCodeDto certifiInfo) {
        //코드 확인 -> 핸드폰 번호로 elderly가 있는지 확인하고 -> 있으면 해당 객체 반환
        return guardianService.checkResister(certifiInfo.getPhoneNumber(),certifiInfo.getCertificationNumber());

    }

    @PatchMapping(value = "/resister")
    public Elderly resisterElderly (@RequestBody RequestElderly updateInfo, @RequestAttribute("member") Member member) {
        return guardianService.resisterElderly(updateInfo, member.getId());
    }
}
