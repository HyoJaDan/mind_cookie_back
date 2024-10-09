package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mindCookie.domain.Member;
import mindCookie.domain.PrimaryHobbit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PrimaryHobbitRepository {

    @PersistenceContext
    private EntityManager em;

    public PrimaryHobbit save(PrimaryHobbit primaryHobbit) {
        em.persist(primaryHobbit);
        return primaryHobbit;
    }
    public void delete(PrimaryHobbit primaryHobbit) {
        em.remove(primaryHobbit);
    }
    public Optional<PrimaryHobbit> findByNameAndMemberId(String primaryHobbitName, Long memberId) {
        TypedQuery<PrimaryHobbit> query = em.createQuery(
                "SELECT ph FROM PrimaryHobbit ph"+
                        " WHERE ph.primaryGoal = :primaryHobbitName"+
                        " AND ph.member.id = :memberId",
                PrimaryHobbit.class);
        query.setParameter("primaryHobbitName", primaryHobbitName);
        query.setParameter("memberId", memberId);
        return query.getResultList().stream().findFirst();
    }
    public Optional<List<PrimaryHobbit>> findByMemberId(Long memberId) {
        List<PrimaryHobbit> result = em.createQuery(
                        "SELECT ph FROM PrimaryHobbit ph" +
                                " JOIN FETCH ph.hobbitList hl" +  // Fetch join을 사용하여 hobbitLists도 함께 로드
                                " WHERE ph.member.id = :memberId"
                        , PrimaryHobbit.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
    public List<PrimaryHobbit> findAllByMember(Member member) {
        return em.createQuery(
                        "SELECT ph FROM PrimaryHobbit ph" +
                                " JOIN FETCH ph.hobbitList hl" +  // Fetch join을 사용하여 hobbitLists도 함께 로드
                                " WHERE ph.member = :member", PrimaryHobbit.class)
                .setParameter("member", member)
                .getResultList();
    }
}
