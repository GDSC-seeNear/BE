package seeNear.seeNear_BE.domain.StatusCheck.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RequestUpdateStatus {
    private String type;

    private boolean done;

    private int chatId;

}
