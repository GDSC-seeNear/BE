package seeNear.seeNear_BE.domain.StatusCheck;

import org.springframework.stereotype.Repository;
import seeNear.seeNear_BE.domain.StatusCheck.domain.Status;
import seeNear.seeNear_BE.domain.StatusCheck.dto.RequestUpdateStatus;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class StatusCheckRepository {
    private final EntityManager em;

    public StatusCheckRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Status status) {
        em.persist(status);
    }
    public Status findStatusByChatId(int chatId) {
        return em.find(Status.class, chatId);
    }

    public void updateStatusByChatIdAndType(int chatId, String type, RequestUpdateStatus status) {
        String jpql = "SELECT s FROM Status s WHERE s.chat.id = :chatId AND s.type = :type";
        List<Status> statuses = em.createQuery(jpql, Status.class)
                .setParameter("chatId", chatId)
                .setParameter("type", type)
                .getResultList();

        if (!statuses.isEmpty()) {
            Status oldStatus = statuses.get(0);
            oldStatus.setDone(status.isDone());
            em.merge(oldStatus);
        }
    }

    public List<Status> getStatusByElderlyIdAndDate(int elderlyId, Date date) {
        String jpql = "SELECT s FROM Status s JOIN s.chat c WHERE c.elderlyId = :elderlyId AND DATE(c.createdAt) = :date";
        List<Status> statuses = em.createQuery(jpql, Status.class)
                .setParameter("elderlyId", elderlyId)
                .setParameter("date", date)
                .getResultList();

        return statuses;
    }

}
