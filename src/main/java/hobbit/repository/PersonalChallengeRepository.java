package hobbit.repository;

import hobbit.domain.EtcGoal;
import hobbit.domain.Exercise;
import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonalChallengeRepository {
    @PersistenceContext
    private EntityManager em;
    public PersonalChallenge findTodayGoalsByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        return em.createQuery(
                        "select pc from PersonalChallenge pc " +
                                "where pc.member.id = :Member_ID " +
                                "and pc.date = :today", PersonalChallenge.class)
                .setParameter("Member_ID", memberId)
                .setParameter("today", today)
                .getSingleResult();
    }
    public Optional<PersonalChallenge> findTodayPersonalChallengeByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        try {
            return Optional.of(em.createQuery(
                            "select pc from PersonalChallenge pc " +
                                    "join fetch pc.etcGoals " +
                                    "where pc.member.id = :Member_ID " +
                                    "and pc.date = :today", PersonalChallenge.class)
                    .setParameter("Member_ID", memberId)
                    .setParameter("today", today)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<PersonalChallenge> findAllByMemberId(Long memberId) {
        return em.createQuery("select pc from PersonalChallenge pc where pc.member.id = :Member_ID", PersonalChallenge.class)
                .setParameter("Member_ID", memberId)
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
