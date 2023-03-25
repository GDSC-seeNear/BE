package seeNear.seeNear_BE.domain.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseChatListDto {
    List<Chat> chatList;
}
