package seeNear.seeNear_BE.global.sms.dto;

import java.util.List;

public class SmsRequestDto {
    String type;
    String from;
    String content;
    List<Message> messages;

    public SmsRequestDto(String type, String from, String content, List<Message> messages) {
        this.type = type;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
