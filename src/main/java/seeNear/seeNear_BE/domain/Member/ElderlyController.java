package seeNear.seeNear_BE.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;

@RestController
@RequestMapping("/elderly")
public class ElderlyController {

    private ElderlyService elderlyService;
    @Autowired
    public ElderlyController(ElderlyService elderlyService) {
        this.elderlyService = elderlyService;
    }

    @PatchMapping(value="/update/{id}")
    public Elderly updateElderly(@RequestBody Elderly updateInfo){
        return elderlyService.update(updateInfo);
    }
}
