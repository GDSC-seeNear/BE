package seeNear.seeNear_BE.domain.Chat;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Chat.dto.RequestChatDto;
import seeNear.seeNear_BE.domain.Chat.dto.ResponseChatListDto;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping (value = "/getChatList/{elderlyId}")
    public ResponseChatListDto getChatList(@PathVariable int elderlyId) {
        var a= chatService.getChatList(elderlyId);
        System.out.println(a);
        return a;
    }

    @PostMapping (value = "/create")
    public String createResponseChat(@RequestBody RequestChatDto requestChatDto) {

        String responseText = chatService.createResponseChat(requestChatDto);

        return responseText;
    }
}
