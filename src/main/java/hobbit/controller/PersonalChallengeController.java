package hobbit.controller;

import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import hobbit.domain.Team;
import hobbit.service.MemberService;
import hobbit.service.PersonalChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonalChallengeController {
    private final PersonalChallengeService personalChallengeService;
    private final MemberService memberService;

    /**
     * 멤버의 personalChallenge 가져오는 함수
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/personal-challenges")
    public List<PersonalChallenge> getPersonalChallanges(@PathVariable Long id){
        List<PersonalChallenge> findChallange= personalChallengeService.getPersonalChallengeRepositoryByMemberId(id);
        return findChallange;
    }

    /**
     * 멤버의 etc목표 추가하는 함수
     * http://localhost:8080/api/member/1/startDay/personal-challenges/addEtcGoals?startDate=2024-03-15T15:00:00
     * @param memberId
     * @param startDate
     * @param goals
     */
    @ResponseBody
    @PutMapping("/api/member/{memberId}/startDay/personal-challenges/addEtcGoals")
    public void addEtcGoals(@PathVariable Long memberId, @RequestParam LocalDateTime startDate, @RequestBody List<String> goals){
        Member findMember = memberService.findOne(memberId);
        personalChallengeService.initEtcGoals(findMember,startDate,goals);
    }
}
