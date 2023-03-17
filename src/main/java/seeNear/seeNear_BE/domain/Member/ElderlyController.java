package seeNear.seeNear_BE.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;

@RestController
@RequestMapping("/elderly")
public class ElderlyController {

    private ElderlyService elderlyService;

    @Autowired
    public ElderlyController(ElderlyService elderlyService) {
        this.elderlyService = elderlyService;
    }

    @GetMapping(value = "/me")
    public Member getElderly(@RequestAttribute("member") Member member) {
        return member;
    }

    @PatchMapping(value = "/update")
    public Elderly updateElderly(@RequestBody RequestElderly updateInfo, @RequestAttribute("member") Member member) {
        //본인만 변경 가능
        return elderlyService.update(updateInfo, member.getId());
    }

    @GetMapping(value = "/{id}")
    public Elderly getElderly(@PathVariable int id) {
        return elderlyService.getElderly(id);
    }
}
