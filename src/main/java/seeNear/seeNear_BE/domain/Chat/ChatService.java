package seeNear.seeNear_BE.domain.Chat;

import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getChatList(int elderlyId) {
        return chatRepository.getChatListByelderlyId(elderlyId);
    }
}
