package seeNear.seeNear_BE.global.infra.sms.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;

    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }
}
