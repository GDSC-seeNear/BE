package seeNear.seeNear_BE.domain.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseChatDto {
    int id;
    int elderlyId;
    String content;
    LocalDateTime createdAt;
    boolean userSend;
    String type;

    public ResponseChatDto(String content) {
        this.content = content;
    }
    public String getCreatedAt() {
        return createdAt.toString();
    }
}
