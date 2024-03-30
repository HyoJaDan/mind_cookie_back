package hobbit.controller.Team;

import hobbit.domain.ChallngeType;
import hobbit.domain.Member;
import hobbit.domain.Team;
import hobbit.service.MemberService;
import hobbit.service.TeamService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final MemberService memberService;

    /**
     * 팀을 생성하는 API
     * @param getTeamDTO
     */
    @PostMapping("/api/team/create-team")
    public void createTeam(@RequestBody getTeamDTO getTeamDTO){
        Member findMember = memberService.findOne(getTeamDTO.memberId);

        teamService.createTeam(findMember,getTeamDTO.teamName,getTeamDTO.startDate,getTeamDTO.challngeType);
    }
    /**
     * 팀 하나 정보 가져오기
     * @param id
     * @return
     */
    @GetMapping("/api/team/{id}")
    public TeamDto requestOneTeam(@PathVariable Long id){
        Team findTeam = teamService.findOne(id);

        return new TeamDto(findTeam);
    }

    /**
     * 모든 팀 정보 가져오기
     * @return
     */
    @GetMapping("/api/team")
    public List<TeamDto> requestAllTeam(){
        List<Team> findAllTeam= teamService.findTeams();

        List<TeamDto> collect = findAllTeam.stream()
                .map(o ->new TeamDto(o))
                .collect(Collectors.toList());

        return collect;
    }
    @ResponseBody
    @GetMapping("api/team/{id}/getEveryData")
    public TeamChallengeDTO getAllMemberId(@PathVariable Long id){
        TeamChallengeDTO todayPersonalChallengeStatusByTeamId = teamService.getTodayPersonalChallengeStatusByTeamId(id);
        return todayPersonalChallengeStatusByTeamId;
    }

    /**
     * 멤버 팀에 소속시키는 함수
     * @param memberId
     * @param teamId
     */
    @ResponseBody
    @PutMapping("/api/member/{memberId}/team/{teamId}")
    public void participapteInTeam(@PathVariable Long memberId, @PathVariable Long teamId){
        Member findMember = memberService.findOne(memberId);
        Team findTeam = teamService.findOne(teamId);

        teamService.addMember(findMember,findTeam);
    }
    @Data
    class TeamDto{
        private Long id;
        private String teamName;
        private Integer maxTeamMemberNumber;
        private LocalDate startDate;
        private LocalDate endDate;
        private ChallngeType challngeType;
        private Integer numOfMember;

        public TeamDto(Team team) {
            this.id=team.getId();
            this.teamName = team.getTeamName();
            this.maxTeamMemberNumber = team.getMaxTeamMemberNumber();
            this.startDate = team.getStartDate();
            this.endDate = team.getEndDate();
            this.challngeType = team.getChallengeType();
            this.numOfMember = team.getMembers().size();
        }
    }
    @Data
    private static class getTeamDTO{
        private Long memberId;
        private String teamName;
        private LocalDate startDate;
        private ChallngeType challngeType;
    }
}
