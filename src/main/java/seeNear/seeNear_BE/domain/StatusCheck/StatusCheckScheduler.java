package seeNear.seeNear_BE.domain.StatusCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class StatusCheckScheduler {
    private final StatusCheckService statusCheckService;

    public StatusCheckScheduler(StatusCheckService statusCheckService) {
        this.statusCheckService = statusCheckService;
    }
    @Scheduled(cron = "0 0 10 * * *")
    public void sendMorningMealCheck() {
        statusCheckService.sendCheckAll("meal1", "아침 식사를 하셨나요?");
    }
    @Scheduled(cron = "0 0 14 * * *")
    public void sendAfternoonMealCheck() {
        statusCheckService.sendCheckAll("meal2", "점심 식사를 하셨나요?");
    }
    @Scheduled(cron = "0 0 19 * * *")
    public void sendDinnerMealCheck() {
        statusCheckService.sendCheckAll("meal3", "저녁 식사를 하셨나요?");
    }
    @Scheduled(cron = "0 10 19 * * *")
    public void sendHealthCheck () {
        statusCheckService.sendCheckAll("health", "컨디션은 괜찮으세요?");
    }
    @Scheduled(cron = "0 10 19 * * *")
    public void sendFeelCheck () {
        statusCheckService.sendCheckAll("feel", "기분은 괜찮으세요?");
    }
    @Scheduled(cron = "0 15 19 * * *")
    public void sendToiletCheck () {
        statusCheckService.sendCheckAll("toilet", "화장실은 다녀오셨나요?");
    }
    @Scheduled(cron = "0 0 20 * * *")
    public void sendPhysicalActivityCheck () {
        statusCheckService.sendCheckAll("physicalActivity", "외부 활동을 하셨나요?");
    }

}
