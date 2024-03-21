package hobbit.controller.MealRecord;

import hobbit.domain.MealRecord;
import hobbit.domain.Picture;
import hobbit.domain.RecordType;
import lombok.*;

import java.time.LocalDateTime;
@Data
public class MealRecordDTO {
    private Long id;
    private LocalDateTime createdTime;
    private int calorie;
    private String content;
    private String title;
    private String imageUrl;
    private RecordType type;
    public MealRecordDTO(MealRecord mealRecord){
        this.id=mealRecord.getId();
        this.createdTime=mealRecord.getCreatedTime();
        this.calorie=mealRecord.getCalorie();
        this.content=mealRecord.getContent();
        this.title=mealRecord.getTitle();
        this.type=mealRecord.getRecordType();
        if (mealRecord.getPicture() != null) {
            this.imageUrl = "http://localhost:8080/api/images/" + mealRecord.getPicture().getStoredFileName();
        }
    }
}
