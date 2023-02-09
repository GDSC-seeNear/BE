package seeNear.seeNear_BE.global.sms;

public class BadNaverSmsRequestException extends RuntimeException{
    private final int statusCode;

    private String statusText;

    public BadNaverSmsRequestException(int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public BadNaverSmsRequestException(int statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }

}
