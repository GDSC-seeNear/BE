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
    public void saveCertificationNum(String requestId,String certificationNum){
        cache.put(requestId,certificationNum);
    }

    @Override
    public String findCertificationNum(String requestId){
        return cache.get(requestId);
    }

    @Override
    public void saveToken(String uuid) {
        store.put(uuid,uuid);
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
