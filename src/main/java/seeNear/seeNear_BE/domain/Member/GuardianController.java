package seeNear.seeNear_BE.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Guardian;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;
import seeNear.seeNear_BE.domain.auth.dto.RequestCheckCodeDto;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    @Autowired
    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping(value = "/managedElderly")
    public List<Elderly> getManagedElderly(@RequestAttribute("member") Guardian member) {
        //토큰에서 아이디 빼서 자기가 관리하는 elderly 반환
        return guardianService.getManagedElderly(member.getId());
    }

    @GetMapping(value = "/managedElderly/{id}")
    public Elderly getOneManagedElderly(@PathVariable int id, @RequestAttribute("member") Guardian member) {
        //토큰에서 아이디 빼서 자기가 관리하는 elderly 반환
        return guardianService.getOneManagedElderly(id, member.getId());
    }

    @GetMapping(value = "/me")
    public Guardian getGuardian(@RequestAttribute("member") Guardian member) {
        return member;
    }

    @PostMapping(value = "/checkResister")
    public Elderly checkResister(@RequestBody RequestCheckCodeDto certifiInfo) {
        //코드 확인 -> 핸드폰 번호로 elderly가 있는지 확인하고 -> 있으면 해당 객체 반환
        return guardianService.checkResister(certifiInfo.getPhoneNumber(), certifiInfo.getCertificationNumber());
    }

    @PatchMapping(value = "/resister")
    public Elderly resisterElderly(@RequestBody RequestElderly updateInfo, @RequestAttribute("member") Member member) {
        //member는 토큰에서 뺀 보호자 id -> 보호자 id랑 updateInfo의 보호자 id랑 같은지 확인
        //checkResister 에서 존재하는 유저인지 확인하기 때문에 불필요
        return guardianService.resisterElderly(updateInfo, member.getId());
    }
}
