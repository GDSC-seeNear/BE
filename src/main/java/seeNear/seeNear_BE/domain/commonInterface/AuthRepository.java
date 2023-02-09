package seeNear.seeNear_BE.domain.commonInterface;


public interface AuthRepository {

    void saveCertificationNum(String requestId,String certificationNum);

    String findCertificationNum(String requestId);

    void saveToken(String uuid);

    String findToken(String uuid);

    void deleteToken(String uuid);

}
