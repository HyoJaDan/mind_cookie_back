package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Hobbit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@Transactional
public class HobbitListRepository {

    @PersistenceContext
    private EntityManager em;

    public boolean save(Hobbit hobbitList) {
        em.persist(hobbitList);
        return true;
    }

    public Optional<Hobbit> findById(Long id) {
        Hobbit hobbit = em.find(Hobbit.class, id);
        return Optional.ofNullable(hobbit);
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
    // hobbit 삭제 메소드
    public void delete(Hobbit hobbit) {
        em.remove(hobbit);
    }

    // PrimaryHobbitId와 hobbitId로 hobbit 찾기
    public Hobbit findByPrimaryHobbitIdAndHobbitId(Long hobbitId) {
        return em.createQuery(
                        "SELECT h FROM Hobbit h "+
                                "WHERE h.id = :hobbitId", Hobbit.class)
                .setParameter("hobbitId", hobbitId)
                .getSingleResult();
    }
}
