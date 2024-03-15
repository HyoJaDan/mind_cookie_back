package hobbit.service;

import hobbit.domain.EtcGoal;
import hobbit.domain.Exercise;
import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import hobbit.repository.PersonalChallengeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalChallengeService {
    @PersistenceContext
    private EntityManager em;
    private final PersonalChallengeRepository personalChallengeRepository;
    public List<EtcGoal> getPersonalChallengeRepositoryByMemberId(Long id) {
        return personalChallengeRepository.findTodayGoalsByMemberId(id);
    }
    public List<EtcGoal> getEtcPersonalChallengeRepositoryByMemberId(Long id){
        return personalChallengeRepository.findTodayEtcGoalsByMemberId(id);
    }

    @Transactional
    public void initEtcGoals(Member member, LocalDateTime startDate, List<String> goals) {
        for(int i=0;i<35;i++)
        {
            PersonalChallenge personalChallenge =
                    new PersonalChallenge(startDate.toLocalDate().plusDays(i),member);
            for (String goal : goals) {
                EtcGoal etcGoal = new EtcGoal(goal, false, personalChallenge);
                personalChallenge.addEtcGoal(etcGoal);
                personalChallengeRepository.saveEtcGoal(etcGoal);
            }
            personalChallengeRepository.savePersonalChallenge(personalChallenge);
        }
    }

    @Transactional
    public void updateEtcGoalDoneStatus(Long etcGoalId, boolean done) {
        personalChallengeRepository.updateEtcGoalDoneStatus(etcGoalId, done);

    }

    @Transactional
    public void updateExerciseAndMemberCalorie(Long personalChallengeId, int exerciseCalorie, boolean isDone) {
        PersonalChallenge personalChallenge = em.find(PersonalChallenge.class, personalChallengeId);
        if (personalChallenge == null) {
            throw new EntityNotFoundException("PersonalChallenge not found");
        }

        Exercise exercise = personalChallenge.getExercise();
        if (exercise == null) {
            throw new IllegalStateException("Exercise component not found");
        }

        // Exercise 업데이트 로직
        exercise.setExerciseCalorie(exerciseCalorie);
        exercise.setDone(isDone);

        // Member의 intakedCalorie 조정
        Member member = personalChallenge.getMember();
        if (member != null) {
            member.setIntakedCalorie(member.getIntakedCalorie() - exerciseCalorie);
            em.merge(member); // Member 엔티티 업데이트
        }
    }

}
