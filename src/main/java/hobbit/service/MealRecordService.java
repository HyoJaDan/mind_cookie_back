package hobbit.service;

import hobbit.domain.*;
import hobbit.repository.MealRecordRepository;
import hobbit.repository.MemberRepository;
import hobbit.repository.PersonalChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealRecordService {
    private final MealRecordRepository mealRecordRepository;
    private final PersonalChallengeRepository personalChallengeRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void save(Long memberId, int calorie, String content, String createdTime, MultipartFile inputedBoardFile, String title, RecordType type) throws IOException {
        MultipartFile boardFile = inputedBoardFile;
        String originalFileName = inputedBoardFile.getOriginalFilename();
        String storedFileName = System.currentTimeMillis() + "_" + originalFileName;

        String savePath = "/Users/jun/picture/" + storedFileName;
        boardFile.transferTo(new File(savePath));
        // 여기까지 경로에 이미지 저장

        // 앞으로 DB에 해당 데이터 save 처리
        // 1.id로 받아온 personalChallenge 가져오기
        PersonalChallenge todayPersonalChallenge = personalChallengeRepository.findTodayGoalsByMemberId(memberId);
        // 2 MealRecord의 Picture 선언
        Picture picture = Picture.toPictureEntity(inputedBoardFile.getOriginalFilename(), storedFileName);
        mealRecordRepository.savePicture(picture);
        // 3. MealRecord 선언
        MealRecord mealRecord = MealRecord.toSaveFileEntity(createdTime,calorie,content,picture,title,type);
        mealRecordRepository.saveMealRecord(mealRecord);
        // 4. 1번에서 찾았던 personalChallenge에 mealRecord 추가 ( 양방향이라 2,3번 선언했던 것들이 모두 연관됨 )
        todayPersonalChallenge.addMealRecord(mealRecord);

        // 멤버의 칼로리 업데이트
        Member findMember = memberRepository.findOne(memberId);
        findMember.setIntakedCalorie(findMember.getIntakedCalorie()+calorie);
    }

    public void get(Long memberId) {
        //return mealRecordRepository.getTodayMealRecordByMemberId(memberId);
    }
}
