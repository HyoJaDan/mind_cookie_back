package hobbit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String userName;
    private String teamUserName;

    public void setTeamUserName(String teamUserName) {
        this.teamUserName = teamUserName;
    }

    private int birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int height;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WeightRecord> weightRecords = new ArrayList<>();
    public void addWeightRecord(WeightRecord record){
        this.weightRecords.add(record);
        record.setMember(this);
    }

    private int calorie;
    private double intakedCalorie;

    public void setIntakedCalorie(double intakedCalorie) {
        this.intakedCalorie = intakedCalorie;
    }

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<PersonalChallenge> personalChallenges=new ArrayList<>();

    public void addPersonalChallenge(PersonalChallenge personalChallenge){
        personalChallenges.add(personalChallenge);
        personalChallenge.setMember(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void setTeam(Team team) {
        this.team = team;
    }

    public Member(){}
    public Member(String userName, int birthYear, Gender gender, int height, int calorie, double intakedCalorie) {
        this.userName = userName;
        this.birthYear = birthYear;
        this.gender = gender;
        this.height = height;
        this.calorie = calorie;
        this.intakedCalorie=intakedCalorie;
    }
}
