package seeNear.seeNear_BE.domain.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestChatDto {
    int elderlyId;
    String content;
    boolean userSend;
    String type;
}
