package hobbit.controller.MealRecord;

import hobbit.domain.MealRecord;
import hobbit.domain.RecordType;
import hobbit.repository.MealRecordRepository;
import hobbit.service.MealRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequiredArgsConstructor
public class MealRecordController {
    private final MealRecordService mealRecordService;

    @PostMapping("api/member/{memberId}/todayPersonalChallenge/saveMealRecord")
    public void save(@PathVariable Long memberId,
                     @RequestParam("calorie") int calorie,
                     @RequestParam("content") String content,
                     @RequestParam("createdTime") String createdTime,
                     @RequestParam("title") String title,
                     @RequestParam("type") RecordType type,
                     @RequestParam("boardFile") MultipartFile boardFile) throws IOException {
        mealRecordService.save(memberId, calorie, content,createdTime,boardFile,title,type);
    }

    //List<Optional<MealRecord>>
    @GetMapping("api/member/{memberId}/todayPersonalChallenge/getPersonalMealRecord")
    public void getMealRecord(@PathVariable Long memberId){
        //List<MealRecord> mealRecords = mealRecordService.get(memberId);
        //return mealRecords;
    }
    private final Path rootLocation = Paths.get("/Users/jun/picture");

    @GetMapping("/api/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
