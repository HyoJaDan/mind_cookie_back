package hobbit.controller.PersonalChallenge;

import hobbit.controller.PersonalChallenge.PersonalChallengeStatusDTO;
import hobbit.domain.*;
import hobbit.service.MemberService;
import hobbit.service.PersonalChallengeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    public PersonalChallenge getPersonalChallanges(@PathVariable Long id){
        PersonalChallenge personalChallenge= personalChallengeService.getPersonalChallengeRepositoryByMemberId(id);

        return personalChallenge;
    }
    /**
     * 멤버의 오늘 personalChallenge 가져오는 함수
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/today-personal-challenges")
    public ResponseEntity<PersonalChallengeStatusDTO> getTodayPersonalChallenges(@PathVariable Long id) {
        //PersonalChallengeStatusDTO statusDto = personalChallengeService.getTodayPersonalChallengeStatusByMemberId(id);
        PersonalChallengeStatusDTO statusDTO2 = personalChallengeService.getPersonalChallengeStatusByMemberId(id);
        if (statusDTO2.getStatus().equals("notFound")) {
            //return ResponseEntity.notFound().build();

            return ResponseEntity.ok(statusDTO2);
        } else {
            return ResponseEntity.ok(statusDTO2);
        }
    }
    /**
     * 멤버의 etc목표 추가하는 함수
     * http://localhost:8080/api/member/1/startDay/personal-challenges/addEtcGoals?startDate=2024-03-15T15:00:00
     * @param memberId
     * @param startDate
     * @param goals
     */
    @ResponseBody
    @PutMapping("/api/member/{id}/startDay/personal-challenges/addEtcGoals")
    public void addEtcGoals(@PathVariable Long id, @RequestParam LocalDate startDate, @RequestBody List<String> goals){
        Member findMember = memberService.findOne(id);
        personalChallengeService.initEtcGoals(findMember,startDate,goals);
    }

    /**
     * etcGoal의 진행 여부 업데이트 API
     * @param etcGoalId
     * @param done
     * @return
     */
    @PutMapping("/api/etc-personal-challenges/{id}/isDone")
    public ResponseEntity<Void> updateEtcGoalDoneStatus(@PathVariable Long id, @RequestParam boolean isDone) {
        personalChallengeService.updateEtcGoalDoneStatus(id, isDone);
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
        personalChallengeService.updateExerciseAndMemberCalorie(id, dto.getExerciseCalorie(),dto.durationInSeconds, dto.isDone());
        return ResponseEntity.ok().build();
    }

    @Data
    private static class ExerciseUpdateDto {
        private double exerciseCalorie;
        private int durationInSeconds;
        private boolean isDone;
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
