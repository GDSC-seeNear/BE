package seeNear.seeNear_BE.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import seeNear.seeNear_BE.domain.Member.MemoryMemberRepository;
import seeNear.seeNear_BE.domain.auth.MemoryAuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.AuthRepository;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;

import java.util.UUID;

public class MemoryAuthRepositoryTest {

    AuthRepository repo = new MemoryAuthRepository();

    @Test
    public void saveToken() {
        UUID uuid = UUID.randomUUID();
        repo.saveToken(uuid.toString());
        String result = repo.findToken(uuid.toString());

        Assertions.assertThat(uuid.toString()).isEqualTo(result);
        repo.deleteToken(uuid.toString());
    }
}
