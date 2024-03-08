package hobbit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="team")
@Getter
public class Team {
    @Id @GeneratedValue
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
    private Integer maxTeamMemberNumber;
    public void addTeamMember(Member member){
        members.add(member);
        member.setTeam(this);
    }

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // startDate 설정 메소드
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate);
    }

    // endDate를 startDate로부터 35일 후로 계산하는 메소드
    private LocalDateTime calculateEndDate(LocalDateTime startDate) {
        return startDate.plusDays(35);
    }

    @Enumerated(EnumType.STRING)
    private ChallngeType challengeType;


    public Team(){}
    public Team(String teamName, Member member,Integer maxTeamMemberNumber, LocalDateTime startDate, ChallngeType challengeType) {
        this.teamName = teamName;
        addTeamMember(member);
        this.maxTeamMemberNumber = maxTeamMemberNumber;
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate);
        this.challengeType = challengeType;
    }
}
