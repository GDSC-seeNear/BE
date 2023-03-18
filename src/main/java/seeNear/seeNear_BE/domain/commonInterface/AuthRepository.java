package seeNear.seeNear_BE.domain.commonInterface;


public interface AuthRepository {

    void saveCertificationNum(String requestId,String certificationNum);

    String findCertificationNum(String requestId);
    void deleteCertificationNum(String phoneNumber);

    void saveToken(String uuid,String phoneNumber);

    String findToken(String uuid);

    void deleteToken(String uuid);


}
