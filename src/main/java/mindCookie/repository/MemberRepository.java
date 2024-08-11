package mindCookie.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public Member findOne(Long id) {
        return em.createQuery(
                "select distinct m from Member m"+
                        " where m.id = :id", Member.class)
                .setParameter("id",id)
                .getSingleResult();
    }
}
