package hobbit.controller;

import hobbit.domain.Member;
import hobbit.domain.WeightRecord;
import hobbit.service.MemberService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 모든 멤버 정보 가져오기 - 실패
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/test/{id}")
    public Member getAllMemberData(@PathVariable Long id){
        Member findMember = memberService.findOneAll(id);

        return findMember;
    }

    /**
     * 특정 맴버 가져오기
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/member/{id}")
    public MemberDto requestMember(@PathVariable Long id){
        Member findMember = memberService.findOne(id);

        return new MemberDto(findMember.getWeightRecords(),findMember.getCalorie(),findMember.getIntakedCalorie());
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
    public MemberDto addWeightRequest(@PathVariable Long id, @RequestParam int weight) {
        memberService.update(id,weight);

        Member findMember=memberService.findOne(id);
        return new MemberDto(findMember.getWeightRecords(),findMember.getCalorie(),findMember.getIntakedCalorie());
    }

    @ResponseBody
    @PutMapping("/api/member/{id}/teamUserName")
    public Member addTeamUserNameRequest(@PathVariable Long id, @RequestParam String teamUserName){
        memberService.updateTeamUserName(id,teamUserName);

        Member findMember=memberService.findOne(id);
        return findMember;
    }
    @Data
    static class MemberDto{
        private List<WeightRecordDto> weight;
        private int calorie;
        private int intakedCalorie;

        public MemberDto(List<WeightRecord> weight, int calorie, int intakedCalorie) {
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

}
