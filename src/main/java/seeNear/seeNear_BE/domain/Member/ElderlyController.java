package seeNear.seeNear_BE.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;

@RestController
@RequestMapping("/elderly")
public class ElderlyController {

    private ElderlyService elderlyService;
    @Autowired
    public ElderlyController(ElderlyService elderlyService) {
        this.elderlyService = elderlyService;
    }

    @GetMapping(value="/me")
    public Member getElderly(@RequestAttribute("member") Member member){
        return member;
    }

    @PatchMapping(value="/update/{id}")
    public Elderly updateElderly(@RequestBody Elderly updateInfo){
        return elderlyService.update(updateInfo);
    }
}
