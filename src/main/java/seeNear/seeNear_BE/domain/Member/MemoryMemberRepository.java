package seeNear.seeNear_BE.domain.Member;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;
import seeNear.seeNear_BE.domain.Member.domain.Member;

import java.util.HashMap;
import java.util.Map;
@Repository
@Transactional
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long,Member> store = new HashMap<>();
    private static final Map<String,Member> store2 = new HashMap<>();


    @Override
    public Member save(Member member) {
        store2.put(member.getPhoneNumber(), member);
        return store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }

    @Override
    public Member findByPhoneNumber(String phoneNumber) {
        return store2.get(phoneNumber);
    }

}
