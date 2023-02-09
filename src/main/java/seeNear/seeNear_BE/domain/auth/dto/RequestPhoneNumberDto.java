package seeNear.seeNear_BE.domain.auth.dto;

public class RequestPhoneNumberDto {
    private String phoneNumber;

    public RequestPhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RequestPhoneNumberDto() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
