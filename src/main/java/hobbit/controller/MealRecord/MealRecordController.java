package hobbit.controller.MealRecord;

import hobbit.repository.MealRecordRepository;
import hobbit.service.MealRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MealRecordController {
    private final MealRecordService mealRecordService;

    @PostMapping("api/member/{memberId}/todayPersonalChallenge/saveMealRecord")
    public void save(@PathVariable Long memberId, @ModelAttribute MealRecordDTO mealRecordDTO) throws IOException {
        mealRecordService.save(memberId,mealRecordDTO);
    }
}
