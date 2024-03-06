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
    private int birthYear;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int height;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WeightRecord> weightRecords = new ArrayList<>();

    private int calorie;
    private int intakedCalorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addWeightRecord(WeightRecord record){
        this.weightRecords.add(record);
        record.setMember(this);
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public Member(){}
    public Member(String userName, int birthYear, Gender gender, int height, int calorie, int intakedCalorie) {
        this.userName = userName;
        this.birthYear = birthYear;
        this.gender = gender;
        this.height = height;
        this.calorie = calorie;
        this.intakedCalorie=intakedCalorie;
    }
}
