package hobbit.repository;

import hobbit.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Member findOneEveryThing(Long id) {
        return em.createQuery(
                        "select distinct m from Member m"+
                                " join fetch m.weightRecords"+
                                " join fetch m.team"+
                                " join fetch m.personalChallenges pc"+
                                " join fetch pc.etcGoals"+
                                " join fetch pc.meals"+
                                " where m.id = :id", Member.class)
                .setParameter("id",id)
                .getSingleResult();
    }
    public Member findOne(Long id) {
        return em.createQuery(
                        "select distinct m from Member m"+
                                " where m.id = :id", Member.class)
                .setParameter("id",id)
                .getSingleResult();
    }
    public Member findOneWithRecords(Long id) {
        return em.createQuery(
                "select distinct m from Member m"+
                        " join fetch m.weightRecords"+
                        " where m.id = :id", Member.class)
                .setParameter("id",id)
                .getSingleResult();
    }

    public Member findMemberWithTeam(Long id) {
        return em.createQuery(
                "select m from Member m"+
                        " left join fetch m.team" +
                        " where m.id = :id", Member.class)
                .setParameter("id",id)
                .getSingleResult();
    }
}
