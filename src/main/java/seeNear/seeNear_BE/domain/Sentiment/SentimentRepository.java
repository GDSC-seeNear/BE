package seeNear.seeNear_BE.domain.Sentiment;

import org.springframework.stereotype.Repository;
import seeNear.seeNear_BE.domain.Sentiment.domain.Sentiment;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class SentimentRepository {
    private final EntityManager em;

    public SentimentRepository(EntityManager em) {
        this.em = em;
    }

    public List<Sentiment> getSentimentByDate(int elderlyId, Date date) {
        String jpql = "SELECT s FROM Sentiment s JOIN s.chat c  WHERE c.elderlyId = :elderlyId AND DATE(c.createdAt) = :date";
        List<Sentiment> resultList = em.createQuery(jpql, Sentiment.class)
                .setParameter("elderlyId", elderlyId)
                .setParameter("date", date)
                .getResultList();

        return resultList;
    }

}
