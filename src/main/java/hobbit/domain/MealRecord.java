package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Entity
@Table(name = "meal_record")
@Getter
public class MealRecord {
    @Id @GeneratedValue
    private Long id;

    private LocalDateTime createdTime;
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    private String title;
    public void setTitle(String title) {
        this.title = title;
    }

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

    public MealRecord(RecordType recordType, String title) {
        this.recordType=recordType;
        this.title=title;
    }

    public static MealRecord toSaveFileEntity(String createdTime, int calorie, String content, Picture picture, String title,RecordType type) {
        MealRecord mealRecord = new MealRecord();

        Instant instant = Instant.parse(createdTime);
        // Instant를 시스템 기본 시간대의 LocalDateTime으로 변환
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        mealRecord.setTitle(title);
        mealRecord.setRecordType(type);
        mealRecord.setCreatedTime(dateTime);
        mealRecord.setCalorie(calorie);
        mealRecord.setContent(content);
        mealRecord.addPicture(picture);
        return mealRecord;
    }

}