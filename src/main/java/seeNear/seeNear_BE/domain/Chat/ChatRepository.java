package seeNear.seeNear_BE.domain.Chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seeNear.seeNear_BE.domain.Chat.domain.Chat;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class ChatRepository {
    private final EntityManager em;
    public ChatRepository(EntityManager em) {
        this.em = em;
    }

    public int save(Chat chat) {
        em.persist(chat);
        return chat.getId();
    }

    public List<Chat> getChatListByElderlyId(int elderlyId) {
        return em.createQuery("select c from Chat c where c.elderlyId = :elderlyId", Chat.class)
                .setParameter("elderlyId", elderlyId)
                .getResultList();
    }

    public List<Date> getDistinctChatDates(int elderlyId) {
        TypedQuery<Date> query = em.createQuery("SELECT DISTINCT DATE(c.createdAt) FROM Chat c WHERE c.elderlyId = :elderlyId", Date.class)
                .setParameter("elderlyId", elderlyId);
        List<Date> result = query.getResultList();

        return result;
    }


}
