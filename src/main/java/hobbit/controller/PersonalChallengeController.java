package hobbit.controller;

import hobbit.domain.*;
import hobbit.service.MemberService;
import hobbit.service.PersonalChallengeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PersonalChallengeController {
    private final PersonalChallengeService personalChallengeService;
    private final MemberService memberService;

    /**
     * 멤버의 오늘 personalChallenge 가져오는 함수
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/personal-challenges")
    public List<EtcGoal> getPersonalChallanges(@PathVariable Long id){
        List<EtcGoal> findChallange= personalChallengeService.getPersonalChallengeRepositoryByMemberId(id);

        return findChallange;
    }
    /**
     * 멤버의 오늘 etc personalChallenge 가져오는 함수
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/etc-personal-challenges")
    public List<EtcGoalDto> getEtcPersonalChallanges(@PathVariable Long id){
        List<EtcGoal> findChallange= personalChallengeService.getEtcPersonalChallengeRepositoryByMemberId(id);

        List<EtcGoalDto> collect = findChallange.stream()
                .map(o ->new EtcGoalDto(o))
                .collect(Collectors.toList());

        return collect;
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

    /**
     * etcGoal의 진행 여부 업데이트 API
     * @param etcGoalId
     * @param done
     * @return
     */
    @PutMapping("/api/goals/{etcGoalId}/done")
    public ResponseEntity<Void> updateEtcGoalDoneStatus(@PathVariable Long etcGoalId, @RequestParam boolean done) {
        personalChallengeService.updateEtcGoalDoneStatus(etcGoalId, done);
        return ResponseEntity.ok().build();
    }

    /**
     * personalChallenge의 exercise를 업데이트 하는 API
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/api/personal-challenge/{id}/exercise")
    public ResponseEntity<Void> updateExercise(@PathVariable Long id, @RequestBody ExerciseUpdateDto dto) {
        personalChallengeService.updateExerciseAndMemberCalorie(id, dto.getExerciseCalorie(), dto.isDone());
        return ResponseEntity.ok().build();
    }

    @Data
    private static class ExerciseUpdateDto {
        private int exerciseCalorie;
        private boolean done;
    }
    @Data
    class EtcGoalDto{
        private Long id;
        private String goalName;
        private Boolean isDone;

        public EtcGoalDto(EtcGoal etcGoal) {
            this.id=etcGoal.getId();
            this.goalName=etcGoal.getGoalName();
            this.isDone=etcGoal.isDone();
        }
    }
}
