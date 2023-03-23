package seeNear.seeNear_BE.domain.Chat;

import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping (value = "/getChatList/{elderlyId}")
    public List<Chat> getChatList(@PathVariable int elderlyId) {
        return chatService.getChatList(elderlyId);
    }

}
