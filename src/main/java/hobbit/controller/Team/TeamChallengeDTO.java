package hobbit.controller.Team;

import hobbit.domain.MealRecord;
import hobbit.domain.Member;
import hobbit.domain.PersonalChallenge;
import hobbit.domain.RecordType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamChallengeDTO {
    private String teamName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalEtcGoals;
    private int completedEtcGoals;
    private int completedExercises;
    private List<MemberDTO> memberDTOS;
    @Data
    public static class MemberDTO{
        private Long memberId;
        private String teamUserName;
        private List<MealRecordDTO> mealRecords;
    }
    @Data
    public static class MealRecordDTO {
        private Long id;
        private LocalDateTime createdTime;
        private String title;
        private String content;
        private RecordType recordType; // 'meal' or 'snack', assuming this information is available
        private String imageUrl; // This will be the storedFileName from the Picture entity

        public MealRecordDTO(MealRecord mealRecord) {
            this.id = mealRecord.getId();
            this.createdTime = mealRecord.getCreatedTime();
            this.title=mealRecord.getTitle();
            this.content = mealRecord.getContent();
            this.recordType = mealRecord.getRecordType();
            if (mealRecord.getPicture() != null) {
                this.imageUrl = "http://localhost:8080/api/images/" + mealRecord.getPicture().getStoredFileName();
            }

        }
    }
}

