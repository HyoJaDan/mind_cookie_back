package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Hobbit;
import org.springframework.stereotype.Repository;

@Repository
public class HobbitListRepository {

    @PersistenceContext
    private EntityManager em;

    public boolean save(Hobbit hobbitList) {
        em.persist(hobbitList);
        return true;
    }
    public Hobbit findByMemberIdAndPrimaryHobbitIdAndHobbitListId(Long memberId, Long primaryHobbitId, Long hobbitListId) {
        return em.createQuery(
                        "SELECT h FROM Hobbit h WHERE h.primaryHobbit.id = :primaryHobbitId " +
                                "AND h.primaryHobbit.member.id = :memberId " +  // MemberId를 포함한 조회
                                "AND h.id = :hobbitListId", Hobbit.class)
                .setParameter("primaryHobbitId", primaryHobbitId)
                .setParameter("memberId", memberId)
                .setParameter("hobbitListId", hobbitListId)
                .getSingleResult();
    }

}
