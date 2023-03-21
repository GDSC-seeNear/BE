package seeNear.seeNear_BE.global.infra.sms.dto;

public class Message {
    private String to;
    private String content;

    public Message(String to,String content) {
        this.to = to;
        this.content = content;
    }

    public String getTo() {
        return to;
    }
    public String getContent() {return content;}
    public void setContent(String content) {
        this.content = content;
    }
    public void setTo(String to) {
        this.to = to;
    }

}
