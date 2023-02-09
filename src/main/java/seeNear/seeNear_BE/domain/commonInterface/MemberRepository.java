package seeNear.seeNear_BE.domain.commonInterface;

import seeNear.seeNear_BE.domain.Member.domain.Member;

import java.util.Map;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long memberId);

    Member findByPhoneNumber(String phoneNumber);
}
