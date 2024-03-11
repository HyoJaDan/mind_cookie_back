package hobbit.service;

import hobbit.domain.PersonalChallenge;
import hobbit.repository.PersonalChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonalChallengeService {
    private final PersonalChallengeRepository personalChallengeRepository;

    public List<PersonalChallenge> getPersonalChallengeRepositoryByMemberId(Long id){
        return personalChallengeRepository.findPersonalChallengesByMemberId(id);
    }

}
