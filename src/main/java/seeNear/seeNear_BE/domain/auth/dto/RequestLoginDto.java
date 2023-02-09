package seeNear.seeNear_BE.domain.auth.dto;

public class RequestLoginDto {
    private String phoneNumber;
    private String requestId;
    private String certificationNumber;

    public RequestLoginDto(String phoneNumber, String requestId, String certificationNumber) {
        this.phoneNumber = phoneNumber;
        this.requestId = requestId;
        this.certificationNumber = certificationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }
}
