package hobbit.controller;

import hobbit.domain.ChallngeType;
import hobbit.domain.Team;
import hobbit.repository.TeamRepository;
import hobbit.service.TeamService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    @GetMapping("/api/team")
    public List<TeamDto> requestTeam(){
        List<Team> findAllTeam= teamService.findTeams();

        List<TeamDto> collect = findAllTeam.stream()
                .map(o ->new TeamDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    @Data
    class TeamDto{
        private String teamName;
        private Integer maxTeamMemberNumber;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private ChallngeType challngeType;
        private Integer numOfMember;

        public TeamDto(Team team) {
            this.teamName = team.getTeamName();
            this.maxTeamMemberNumber = team.getMaxTeamMemberNumber();
            this.startDate = team.getStartDate();
            this.endDate = team.getEndDate();
            this.challngeType = team.getChallengeType();
            this.numOfMember = team.getMembers().size();
        }
    }

}
