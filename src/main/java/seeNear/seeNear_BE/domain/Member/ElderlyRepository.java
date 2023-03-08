package seeNear.seeNear_BE.domain.Member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class ElderlyRepository {
    private final EntityManager em;
    public ElderlyRepository(EntityManager em) {
        this.em = em;
    }

    public Elderly save(Elderly elderly) {
        em.persist(elderly);
        return elderly;
    }


    public Elderly findById(int elderlyId) {
        Elderly elderly = em.find(Elderly.class, elderlyId);
        return elderly;
    }


    public Elderly findByPhoneNumber(String phoneNumber) {
        List<Elderly> elderly = em.createQuery("select e from Elderly e where e.phoneNumber = :phoneNumber", Elderly.class)
                .setParameter("phoneNumber",phoneNumber)
                .getResultList();

        return elderly.size() ==1 ? elderly.get(0) : null;
    }

    public Elderly update(Elderly elderly) {
        Elderly newElderly = em.merge(elderly);

        return newElderly;
    }

}
