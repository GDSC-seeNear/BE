package seeNear.seeNear_BE.domain.NamedEntity;

import org.springframework.stereotype.Repository;
import seeNear.seeNear_BE.domain.NamedEntity.domain.NamedEntity;
import seeNear.seeNear_BE.domain.NamedEntity.dto.ResponseNamedEntityDto;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NamedEntityRepository {

    private final EntityManager em;


    public NamedEntityRepository(EntityManager em) {
        this.em = em;
    }


    public List<ResponseNamedEntityDto> getNamedEntityByDate(int elderlyId, Date date) {
        String jpql = "SELECT n,c.content FROM NamedEntity n JOIN n.chat c  WHERE c.elderlyId = :elderlyId AND DATE(c.createdAt) = :date";
        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .setParameter("elderlyId", elderlyId)
                .setParameter("date", date)
                .getResultList();

        List<ResponseNamedEntityDto> responseList = resultList.stream()
                .map(result -> {
                    NamedEntity namedEntity = (NamedEntity) result[0];
                    String content = (String) result[1];
                    return new ResponseNamedEntityDto(namedEntity.getId(), namedEntity.getType(), namedEntity.getTarget(), namedEntity.getCreatedAt(), content);
                })
                .collect(Collectors.toList());
        return responseList;
    }
}
