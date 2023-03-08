package seeNear.seeNear_BE.domain.Member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.Member.domain.Guardian;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class GuardianRepository {

    private final EntityManager em;
    public GuardianRepository(EntityManager em) {
        this.em = em;
    }

    public Guardian save(Guardian guardian) {
        em.persist(guardian);
        return guardian;
    }


    public Guardian findById(int guardianId) {
        Guardian guardian = em.find(Guardian.class, guardianId);
        return guardian;
    }


    public Guardian findByPhoneNumber(String phoneNumber) {
        List<Guardian> guardian = em.createQuery("select g from Guardian g where g.phoneNumber = :phoneNumber", Guardian.class)
                .setParameter("phoneNumber",phoneNumber)
                .getResultList();

        return guardian.size() ==1 ? guardian.get(0) : null;
    }
}
