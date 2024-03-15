package hobbit.repository;

import hobbit.domain.EtcGoal;
import hobbit.domain.Exercise;
import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PersonalChallengeRepository {
    @PersistenceContext
    private EntityManager em;
    public List<EtcGoal> findTodayGoalsByMemberId(Long memberId) { LocalDate today = LocalDate.now();
        return em.createQuery(
                        "select pc from PersonalChallenge pc " +
                                "where pc.member.id = :Member_ID " +
                                "and pc.date = :today", EtcGoal.class)
                .setParameter("Member_ID", memberId)
                .setParameter("today", today)
                .getResultList();
    }
    public List<EtcGoal> findTodayEtcGoalsByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        return em.createQuery(
                        "select eg from PersonalChallenge pc " +
                                "join pc.etcGoals eg " +
                                "where pc.member.id = :Member_ID " +
                                "and pc.date = :today", EtcGoal.class)
                .setParameter("Member_ID", memberId)
                .setParameter("today", today)
                .getResultList();
    }

    public void saveEtcGoal(EtcGoal etcGoal) {
        em.persist(etcGoal);
    }

    public void savePersonalChallenge(PersonalChallenge personalChallenge) {
        em.persist(personalChallenge);
    }

    public void updateEtcGoalDoneStatus(Long etcGoalId, boolean done) {
        em.createQuery("update EtcGoal eg set eg.isDone = :done where eg.id = :etcGoalId")
                .setParameter("done", done)
                .setParameter("etcGoalId", etcGoalId)
                .executeUpdate();
    }

}
