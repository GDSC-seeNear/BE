package seeNear.seeNear_BE.domain.Member;

import seeNear.seeNear_BE.domain.commonInterface.MemberRepository;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
