package seeNear.seeNear_BE.domain.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.domain.Chat.interceptor.HttpHandshakeInterceptor;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private HttpHandshakeInterceptor httpHandshakeInterceptor;
    @Autowired
    private WebSocketHandler chatHandler;

    @Bean
    public WebSocketHandler chatHandler(ObjectMapper objectMapper,ChatService chatService) {
        return new ChatHandler(objectMapper,chatService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("registerWebSocketHandlers");
        registry.addHandler(chatHandler, "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(httpHandshakeInterceptor);
    }




}

