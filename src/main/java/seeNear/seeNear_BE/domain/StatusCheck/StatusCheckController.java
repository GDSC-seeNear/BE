package seeNear.seeNear_BE.domain.StatusCheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.StatusCheck.dto.RequestUpdateStatus;

@RestController
@RequestMapping("/statusCheck")
public class StatusCheckController {

    private final StatusCheckService statusCheckService;

    public StatusCheckController(StatusCheckService statusCheckService) {
        this.statusCheckService = statusCheckService;
    }

    @PatchMapping(value = "/update/{type}/{chatId}")
    public ResponseEntity<String> updateStatusCheck(@PathVariable String type,@PathVariable int chatId, @RequestBody RequestUpdateStatus status) {
        statusCheckService.updateStatusCheck(chatId,type,status);
        return ResponseEntity.ok().body("업데이트가 정상적으로 진행 되었습니다.");
    }


    @GetMapping(value = "/send")
    public void sendStatusCheck() {
    }
}
