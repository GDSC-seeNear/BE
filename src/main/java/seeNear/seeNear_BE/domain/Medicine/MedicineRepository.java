package seeNear.seeNear_BE.domain.Medicine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class MedicineRepository {
    private final EntityManager em;
    public MedicineRepository(EntityManager em) {
        this.em = em;
    }

    public Medicine save(Medicine medicine) {
        em.persist(medicine);
        return medicine;
    }

    public Medicine findById(int medicineId) {
        Medicine medicine = em.find(Medicine.class, medicineId);
        return medicine;
    }

    public List<Medicine> findByElderlyId(int elderlyId) {
        List<Medicine> medicine = em.createQuery("select m from Medicine m where m.elderlyId = :elderlyId", Medicine.class)
                .setParameter("elderlyId",elderlyId)
                .getResultList();

        return medicine;
    }

    public Medicine update(Medicine medicine) {
        Medicine newMedicine = em.merge(medicine);
        return newMedicine;
    }

    public void delete(Medicine medicine) {
        em.remove(medicine);
    }

}
