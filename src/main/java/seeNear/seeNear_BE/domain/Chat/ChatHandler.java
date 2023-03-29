package seeNear.seeNear_BE.domain.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.messaging.Message;
import seeNear.seeNear_BE.domain.Chat.dto.RequestChatDto;
import seeNear.seeNear_BE.domain.Chat.dto.ResponseChatDto;


@Slf4j
public class ChatHandler implements WebSocketHandler {
    private ObjectMapper objectMapper;
    private ChatService chatService;

    // CopyOnWriteArrayList,ConcurrentHashMap 스레드 안전한 리스트,해시 맵으로, 다중 스레드에서 안전하게 리스트에 접근할 수 있도록 해주기 때문
    // private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final Map<Integer,WebSocketSession> connectedUser = new ConcurrentHashMap<>();

    public ChatHandler(ObjectMapper objectMapper, ChatService chatService) {
        this.objectMapper = objectMapper;
        this.chatService = chatService;
    }

    //WebSocket 연결이 성립되면 세션을 리스트에 추가
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        int elderlyId = (int) session.getAttributes().get("elderlyId");
        connectedUser.put(elderlyId,session);
        for (Map.Entry<Integer, WebSocketSession> entry : connectedUser.entrySet()) {
            Integer key = entry.getKey();
            WebSocketSession value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    public <T> T parseMessage(WebSocketMessage<?> message, Class<T> changeDto) throws IOException {
        //메시지를 파싱해서 처리
        String payload = (String) message.getPayload();
        T changedDTO = null;
        try {
            changedDTO = objectMapper.readValue(payload, changeDto);
            // DTO 객체로부터 필요한 작업 수행
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("메시지를 파싱하는데 실패했습니다.");
        }
        return changedDTO;
    }
    //클라이언트로부터 수신한 메시지에 답장
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        RequestChatDto requestChat = parseMessage(message, RequestChatDto.class);
        log.info("payload : {}", payload);
        ResponseChatDto responseChat = chatService.createResponseChat(requestChat);

        String jsonResponse = objectMapper.writeValueAsString(responseChat);
        session.sendMessage(new TextMessage(jsonResponse));
    }


    //세션을 삭제
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        int elderlyId = (int) session.getAttributes().get("elderlyId");
        connectedUser.remove(elderlyId);
        log.info("웹소켓 에러 비정상 종료");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        int elderlyId = (int) session.getAttributes().get("elderlyId");
        connectedUser.remove(elderlyId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}

