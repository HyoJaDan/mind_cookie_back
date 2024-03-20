package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="personal_challenge")
@Getter
public class PersonalChallenge {
    @Id @GeneratedValue
    private Long id;
    private LocalDate date; // 대상 날짜

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Member_ID")
    private Member member; // 연관된 멤버
    public void setMember(Member member) {
        this.member = member;
    }

    // 기타 목표와 달성 여부
    @OneToMany(mappedBy = "personalChallenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtcGoal> etcGoals=new ArrayList<>();

    // 운동 기록
    @Embedded
    private Exercise exercise;

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @OneToMany(mappedBy = "personalChallenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealRecord> mealRecords=new ArrayList<>();

    public void addMealRecord(MealRecord mealRecord){
        mealRecords.add(mealRecord);
        mealRecord.setPersonalChallenge(this);
    }

    public PersonalChallenge() {
    }

    public PersonalChallenge(LocalDate date, Member member) {
        this.date = date;
        this.member = member;
        this.exercise=new Exercise();
    }

    public void addEtcGoal(EtcGoal etcGoal) {
        etcGoals.add(etcGoal);
        etcGoal.setPersonalChallenge(this);
    }
}
