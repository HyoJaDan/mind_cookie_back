package hobbit.service;

import hobbit.controller.PersonalChallenge.PersonalChallengeDTO;
import hobbit.controller.PersonalChallenge.PersonalChallengeStatusDTO;
import hobbit.domain.*;
import hobbit.repository.MealRecordRepository;
import hobbit.repository.PersonalChallengeRepository;
import hobbit.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalChallengeService {
    @PersistenceContext
    private EntityManager em;
    private final PersonalChallengeRepository personalChallengeRepository;
    private final MealRecordRepository mealRecordRepository;
    private final TeamRepository teamRepository;
    public PersonalChallenge getPersonalChallengeRepositoryByMemberId(Long id) {
        return personalChallengeRepository.findTodayGoalsByMemberId(id);
    }

    public PersonalChallengeStatusDTO getPersonalChallengeStatusByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        List<PersonalChallenge> challenges = personalChallengeRepository.findAllByMemberId(memberId);
        Team findTeam = teamRepository.findTeamByMemberId(memberId);

        // 첼린지가 진행 중인 경우
        for (PersonalChallenge challenge : challenges) {
            if (today.equals(challenge.getDate())) {
                Optional<List<MealRecord>> todayMealRecordByMemberId = mealRecordRepository.getTodayMealRecordByMemberId(memberId);
                if(!todayMealRecordByMemberId.isPresent()) {
                    //음식 이미지가 존재하지 않을 경우
                    return new PersonalChallengeStatusDTO("active", new PersonalChallengeDTO(challenge),findTeam);
                } else {
                    List<MealRecord> mealRecords = todayMealRecordByMemberId.get();
                    return new PersonalChallengeStatusDTO("active", new PersonalChallengeDTO(challenge,mealRecords),findTeam);
                }
            }
        }
        LocalDate startDate = challenges.get(0).getDate();
        LocalDate endDate = challenges.get(challenges.size() - 1).getDate().plusDays(34); // 마지막 challenge의 날짜에 34일 추가

        // 현재 날짜가 challenges의 첫 번째 데이터의 날짜보다 이전인 경우
        if (today.isBefore(startDate)) {
            return new PersonalChallengeStatusDTO("before",findTeam);
        }
        // 현재 날짜가 challenges의 마지막 데이터의 날짜보다 이후인 경우
        else if (today.isAfter(endDate)) {
            return new PersonalChallengeStatusDTO("after",findTeam);
        }

        // memberId에 해당하는 PersonalChallenge가 전혀 없는 경우
        return new PersonalChallengeStatusDTO("notFound",findTeam);
    }

    /**
     *
     *  public PersonalChallengeStatusDTO getTodayPersonalChallengeStatusByMemberId(Long memberId) {
     *         LocalDate today = LocalDate.now();
     *         Optional<PersonalChallenge> challengeOpt = personalChallengeRepository.findTodayPersonalChallengeByMemberId(memberId);
     *
     *
     *         if (!challengeOpt.isPresent()) {
     *             // 첼린지가 존재하지 않는 경우
     *             return new PersonalChallengeStatusDTO("notFound");
     *         } else {
     *             PersonalChallenge challenge = challengeOpt.get();
     *             if (today.isBefore(challenge.getDate())) {
     *                 // 오늘 날짜가 첼린지 시작 날짜 전인 경우
     *                 return new PersonalChallengeStatusDTO("before");
     *             } else if (today.isAfter(challenge.getDate())) {
     *                 // 오늘 날짜가 첼린지 시작 날짜 후인 경우
     *                 return new PersonalChallengeStatusDTO("after");
     *             } else {
     *                 // 첼린지가 진행 중인 경우
     *                 Optional<List<MealRecord>> todayMealRecordByMemberId = mealRecordRepository.getTodayMealRecordByMemberId(memberId);
     *                 if(!todayMealRecordByMemberId.isPresent()) {
     *                     //음식 이미지가 존재하지 않을 경우
     *                     return new PersonalChallengeStatusDTO("active", new PersonalChallengeDTO(challenge));
     *                 } else {
     *                     List<MealRecord> mealRecords = todayMealRecordByMemberId.get();
     *                     return new PersonalChallengeStatusDTO("active", new PersonalChallengeDTO(challenge,mealRecords));
     *                 }
     *
     *             }
     *         }
     *     }
     * @param memberId
     * @return
     */




    @Transactional
    public void initEtcGoals(Member member, LocalDate startDate, List<String> goals) {
        System.out.println("member = " + member.getId());
        System.out.println("startDate = " + startDate);
        for (String goal : goals) {
            System.out.println("goal = " + goal);
        }
        System.out.println("DONE = " );
        for(int i=0;i<35;i++)
        {
            PersonalChallenge personalChallenge =
                    new PersonalChallenge(startDate.plusDays(i),member);
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
    public void updateExerciseAndMemberCalorie(Long personalChallengeId, double exerciseCalorie, int durationInSeconds, boolean isDone) {
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
        exercise.setDurationInSeconds(durationInSeconds);

        // Member의 intakedCalorie 조정
        Member member = personalChallenge.getMember();
        if (member != null) {
            member.setIntakedCalorie(member.getIntakedCalorie() - exerciseCalorie);
            em.merge(member); // Member 엔티티 업데이트
        }
    }

}
