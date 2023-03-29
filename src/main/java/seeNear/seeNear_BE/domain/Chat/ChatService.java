package seeNear.seeNear_BE.domain.Chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;
import seeNear.seeNear_BE.domain.Chat.dto.RequestChatDto;
import seeNear.seeNear_BE.domain.Chat.dto.ResponseChatDto;
import seeNear.seeNear_BE.domain.Chat.dto.ResponseChatListDto;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Value("${ai.server_ip}")
    private String aiServerIp;
    @Value("${ai.server_port}")
    private String aiServerPort;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public ResponseChatListDto getChatList(int elderlyId) {
        var chatList = chatRepository.getChatListByelderlyId(elderlyId);
        System.out.println(chatList);
        return new ResponseChatListDto(chatList);
    }

    public ResponseChatDto createResponseChat(RequestChatDto requestChatDto) {
        WebClient client = WebClient.create();
        System.out.println(requestChatDto);
        ResponseChatDto responseText = client.post()
                .uri(String.format("http://%s:%s",aiServerIp,aiServerPort))
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(requestChatDto)
                .retrieve()
                .bodyToMono(ResponseChatDto.class)
                .block();
        System.out.println(responseText);


        responseText = responseText == null ? new ResponseChatDto("죄송합니다. 무슨 말인지 모르겠어요.") : responseText;

        return responseText;
    }
}
