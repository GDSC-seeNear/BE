package seeNear.seeNear_BE.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode(),e.getValue());
    }

    @ExceptionHandler(Exception.class)
    public void handleWebsocketCustomException(Exception ex, WebSocketSession session) throws IOException {
        log.error("Error occurred in WebSocketHandler: {}", ex.getMessage());
        session.sendMessage(new TextMessage("An error occurred while processing your request on websocket."));
    }
}
