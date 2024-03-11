package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meal_record")
@Getter
public class MealRecord {
    @Id @GeneratedValue
    private Long id;

    private String picture;
    private int calorie;
    private LocalTime time;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mealRecord_to_PersonalChallenge")
    private PersonalChallenge personalChallenge;

    public void setPersonalChallenge(PersonalChallenge personalChallenge) {
        this.personalChallenge = personalChallenge;
    }

    public MealRecord() {
    }

    public MealRecord(String picture, int calorie, LocalTime time, PersonalChallenge personalChallenge) {
        this.picture = picture;
        this.calorie = calorie;
        this.time = time;
        this.personalChallenge = personalChallenge;
    }
}