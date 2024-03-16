package hobbit.controller;

import hobbit.domain.Member;
import hobbit.domain.WeightRecord;
import hobbit.service.MemberService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * Member의 모든 정보 가져오기
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}")
    public Member getAllMemberData(@PathVariable Long id){
        Member findMember = memberService.findOne(id);

        return findMember;
    }

    /**
     * 멤버가 팀에 참가했는지 확인하는 API
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/isMemberWithTeam")
    public ResponseEntity<Boolean> isMemberPartOfTeam(@PathVariable Long id) {
        boolean isPartOfTeam = memberService.isMemberPartOfTeam(id);
        return ResponseEntity.ok(isPartOfTeam);
    }
    /**
     * 내 기록에서 맴버 가져오기
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/myRecord")
    public MemberMyRecordDto requestMemberInProfile(@PathVariable Long id){
        Member findMember = memberService.findOneWithRecords(id);

        return new MemberMyRecordDto(findMember.getWeightRecords(),findMember.getCalorie(),findMember.getIntakedCalorie());
    }

    /**
     * 첼린지 참가할때 사용하는 API
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}/profile")
    public MemberProfileDto requestMemberUserName(@PathVariable Long id){
        Member findMember = memberService.findOne(id);

        return new MemberProfileDto(findMember.getUserName());
    }

    /**
     * Member의 팀에서 사용할 닉네임 추가 함수
     * @param id
     * @param teamUserName
     * @return
     */
    @ResponseBody
    @PutMapping("/api/member/{id}/teamUserName")
    public Member addTeamUserNameRequest(@PathVariable Long id, @RequestParam String teamUserName){
        memberService.updateTeamUserName(id,teamUserName);

        Member findMember=memberService.findOne(id);
        return findMember;
    }

    /**
     * 몸무게 업데이트 하는 함수
     * @param id
     * @param weight
     * @return
     */
    @ResponseBody
    // http://localhost:8080/api/member/1/weight?weight=68
    @PutMapping("/api/member/{id}/weight")
    public MemberMyRecordDto addWeightRequest(@PathVariable Long id, @RequestParam int weight) {
        memberService.update(id,weight);

        Member findMember=memberService.findOneWithRecords(id);
        return new MemberMyRecordDto(findMember.getWeightRecords(),findMember.getCalorie(),findMember.getIntakedCalorie());
    }
    @Data
    static class MemberMyRecordDto{
        private List<WeightRecordDto> weight;
        private int calorie;
        private double intakedCalorie;

        public MemberMyRecordDto(List<WeightRecord> weight, int calorie, double intakedCalorie) {
            this.weight=weight.stream()
                    .map(weights -> new WeightRecordDto(weights))
                    .collect(Collectors.toList());
            this.calorie = calorie;
            this.intakedCalorie=intakedCalorie;
        }
    }
    @Getter
    static class WeightRecordDto{
        private LocalDateTime date;
        private Integer weight;

        public WeightRecordDto(WeightRecord weightRecord){
            this.date=weightRecord.getDate();
            this.weight=weightRecord.getWeight();
        }
    }
    @Data
    static class MemberProfileDto{
        private String userName;

        public MemberProfileDto(String userName) {
            this.userName=userName;
        }
    }
}
