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


@Slf4j
public class ChatHandler implements WebSocketHandler {
    private ObjectMapper objectMapper;
    private ChatService chatService;

    // CopyOnWriteArrayList,ConcurrentHashMap 스레드 안전한 리스트,해시 맵으로, 다중 스레드에서 안전하게 리스트에 접근할 수 있도록 해주기 때문
    //private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
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
    //클라이언트로부터 수신한 메시지를 리스트에 있는 모든 세션에 전송
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        RequestChatDto requestChat = parseMessage(message, RequestChatDto.class);
        log.info("payload : {}", payload);
        String responseChat = chatService.createResponseChat(requestChat);
        session.sendMessage(new TextMessage(responseChat));
    }

    //chat을 유저에게 보내는 메소드
    @MessageMapping("/chat/{roomId}")
    public void handleChatMessage(WebSocketSession session,@DestinationVariable String roomId, WebSocketMessage<?> message) throws Exception {

        RequestChatDto requestChat = parseMessage(message, RequestChatDto.class);
        int elderlyId = requestChat.getElderlyId();
        int tokenElderlyId = (int) session.getAttributes().get("elderlyId");
        if(elderlyId != tokenElderlyId){
            throw new Exception("토큰에 있는 elderlyId와 메시지에 있는 elderlyId가 다릅니다");
        }
        String responseChat = chatService.createResponseChat(requestChat);
        session.sendMessage(new TextMessage(responseChat));

//        List<WebSocketSession> sessions = chatRooms.getOrDefault(roomId, new CopyOnWriteArrayList<>());
//        for (WebSocketSession session : sessions) {
//            session.sendMessage(new TextMessage(chatMessage.getContent()));
//        }

    }


    //세션을 삭제
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        if (session.isOpen()) {
//            session.close();
//        }
//        sessions.remove(session);
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

