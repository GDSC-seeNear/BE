package seeNear.seeNear_BE.domain.StatusCheck;

import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Chat.ChatRepository;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.StatusCheck.domain.Status;
import seeNear.seeNear_BE.domain.StatusCheck.dto.RequestUpdateStatus;

@Service
public class StatusCheckService {

    private final ElderlyRepository elderlyRepository;
    private final ChatRepository chatRepository;
    private final StatusCheckRepository statusCheckRepository;

    public StatusCheckService(ElderlyRepository elderlyRepository, ChatRepository chatRepository,StatusCheckRepository statusCheckRepository) {
        this.elderlyRepository = elderlyRepository;
        this.chatRepository = chatRepository;
        this.statusCheckRepository = statusCheckRepository;
    }

    public void sendCheckAll(String type, String message) {
        //db 넣고, chatId를 받아서 statusCheck에 넣는다.
        elderlyRepository.findAll().forEach(elderly -> {
            int chatId = chatRepository.save(new Chat(elderly.getId(), message, false, type));
            statusCheckRepository.save(new Status(type, false, chatId));
        });
    }

    public void updateStatusCheck(int chatId,String type, RequestUpdateStatus status) {
        statusCheckRepository.updateStatusByChatIdAndType(chatId, type, status);
    }



}
