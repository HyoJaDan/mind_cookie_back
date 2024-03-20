package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hobbit.controller.MealRecord.MealRecordDTO;
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

    private LocalDateTime createdTime;
    private int calorie;

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    @Column(length = 500)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mealRecord_to_PersonalChallenge")
    private PersonalChallenge personalChallenge;

    public void setPersonalChallenge(PersonalChallenge personalChallenge) {
        this.personalChallenge = personalChallenge;
    }

    @OneToOne
    @JoinColumn(name = "PICTURE_ID")
    private Picture picture;

    public void addPicture(Picture picture){
        this.picture=picture;
        picture.setMealRecord(this);
    }

    public MealRecord() {
    }

    public MealRecord(int calorie, LocalTime time,String content, PersonalChallenge personalChallenge) {
        this.calorie = calorie;
        this.content = content;
        this.personalChallenge = personalChallenge;
    }

    public static MealRecord toSaveFileEntity(MealRecordDTO mealRecordDTO,Picture picture) {
        MealRecord mealRecord = new MealRecord();
        mealRecord.setCalorie(mealRecordDTO.getCalorie());
        mealRecord.setContent(mealRecordDTO.getContent());
        mealRecord.addPicture(picture);
        return mealRecord;
    }

}