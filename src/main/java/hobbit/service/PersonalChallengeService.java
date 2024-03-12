package hobbit.service;

import hobbit.domain.EtcGoal;
import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import hobbit.repository.PersonalChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalChallengeService {
    private final PersonalChallengeRepository personalChallengeRepository;

    public List<PersonalChallenge> getPersonalChallengeRepositoryByMemberId(Long id){
        return personalChallengeRepository.findPersonalChallengesByMemberId(id);
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
}
