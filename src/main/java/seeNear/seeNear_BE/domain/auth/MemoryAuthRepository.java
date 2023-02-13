package seeNear.seeNear_BE.domain.auth;

import org.springframework.stereotype.Repository;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class MemoryAuthRepository implements AuthRepository{
    private static final Map<String,String> store = new HashMap<>();
    private static final Map<String,String> cache = new HashMap<>();



    @Override
    public void saveCertificationNum(String phoneNumber,String certificationNum){
        cache.put(phoneNumber,certificationNum);
    }

    @Override
    public String findCertificationNum(String phoneNumber){
        return cache.get(phoneNumber);
    }

    @Override
    public void saveToken(String uuid,String token) {
        store.put(uuid,token);
    }

    @Override
    public String findToken(String uuid) {
        return store.get(uuid);
    }

    @Override
    public void deleteToken(String uuid) {
        store.remove(uuid);
    }
}
