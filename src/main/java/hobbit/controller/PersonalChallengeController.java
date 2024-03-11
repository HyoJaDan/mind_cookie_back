package hobbit.controller;

import hobbit.domain.PersonalChallenge;
import hobbit.service.PersonalChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonalChallengeController {
    private final PersonalChallengeService personalChallengeService;

    @ResponseBody
    @GetMapping("/members/{id}/personal-challenges")
    public List<PersonalChallenge> getPersonalChallanges(@PathVariable Long id){
        List<PersonalChallenge> findChallange= personalChallengeService.getPersonalChallengeRepositoryByMemberId(id);
        return findChallange;
    }
}
