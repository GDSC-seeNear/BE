package seeNear.seeNear_BE.global.infra.sms.dto;

public class CertificationInfo {
    String requestId;
    String certificationNumber;

    public CertificationInfo(String requestId, String certificationNumber) {
        this.requestId = requestId;
        this.certificationNumber = certificationNumber;
    }

    public CertificationInfo() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }
}
