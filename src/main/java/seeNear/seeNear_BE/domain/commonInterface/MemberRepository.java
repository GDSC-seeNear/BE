package seeNear.seeNear_BE.domain.commonInterface;

import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;

import java.util.Map;
import java.util.Optional;

public interface MemberRepository {

    Elderly save(Elderly Elderly);

    Elderly findById(int ElderlyId);

    Elderly findByPhoneNumber(String phoneNumber);
}
