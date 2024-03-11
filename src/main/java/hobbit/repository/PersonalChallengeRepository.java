package hobbit.repository;

import hobbit.domain.PersonalChallenge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonalChallengeRepository {
    @PersistenceContext
    private EntityManager em;

    public List<PersonalChallenge> findPersonalChallengesByMemberId(Long id){
        return em.createQuery(
                "select distinct pc from PersonalChallenge pc"+
                        " left join fetch pc.etcGoals"+
                        " where pc.member.id = :Member_ID",PersonalChallenge.class)
                .setParameter("Member_ID",id)
                .getResultList();
    }
}
