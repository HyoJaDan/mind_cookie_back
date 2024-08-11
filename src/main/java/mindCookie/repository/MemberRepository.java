package mindCookie.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public Optional<Member> findById(Long id) {
        return em.createQuery(
                        "select distinct m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }
}
