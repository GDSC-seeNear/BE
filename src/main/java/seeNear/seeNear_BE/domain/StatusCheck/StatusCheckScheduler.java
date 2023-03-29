package seeNear.seeNear_BE.domain.StatusCheck;

import org.springframework.stereotype.Component;


@Component
public class StatusCheckScheduler {
    private final StatusCheckService statusCheckService;

    public StatusCheckScheduler(StatusCheckService statusCheckService) {
        this.statusCheckService = statusCheckService;
    }
    //@Scheduled(cron = "* * * * * *")
    public void sendMealCheck() {
        statusCheckService.sendCheckAll("meal1", "식사를 하셨나요?");
    }

    public void sendHealthCheck () {
        statusCheckService.sendCheckAll("health", "컨디션은 괜찮으세요?");
    }

    public void sendFeelCheck () {
        statusCheckService.sendCheckAll("feel", "기분은 괜찮으세요?");
    }

    public void sendToiletCheck () {
        statusCheckService.sendCheckAll("toilet", "화장실은 다녀오셨나요?");
    }

    public void sendPhysicalActivityCheck () {
        statusCheckService.sendCheckAll("physicalActivity", "외부 활동을 하셨나요?");
    }

}
