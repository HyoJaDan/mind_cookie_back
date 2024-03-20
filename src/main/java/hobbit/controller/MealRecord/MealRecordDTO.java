package hobbit.controller.MealRecord;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
public class MealRecordDTO {
    private LocalDateTime createdTime;
    private int calorie;
    private String content;

    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름

}
