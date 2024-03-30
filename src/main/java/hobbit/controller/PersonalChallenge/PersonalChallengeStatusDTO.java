package hobbit.controller.PersonalChallenge;
import hobbit.domain.Team;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalChallengeStatusDTO {
    private String status; // "before", "active", "after", "not Found"
    private PersonalChallengeDTO challenge;
    private TeamDTO team;
    // Constructor for status without challenge data
    public PersonalChallengeStatusDTO(String status, Team team) {
        this.status = status;
        this.team = new TeamDTO();
        this.team.TeamId=team.getId();
        this.team.TeamName=team.getTeamName();
    }

    // Constructor for status with challenge data
    public PersonalChallengeStatusDTO(String status, PersonalChallengeDTO challenge, Team team) {
        this.status = status;
        this.challenge = challenge;
        this.team = new TeamDTO();
        this.team.TeamId=team.getId();
        this.team.TeamName=team.getTeamName();
    }
    @Data
    private static class TeamDTO {
        private Long TeamId;
        private String TeamName;
    }
}
