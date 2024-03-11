package hobbit;

import hobbit.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    // @PostConstruct : 가장 편리하게 초기화와를 할수 있는 방법
    // @PreDestroy : 반대로 종료를 할수 있는 방법
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1() {
            Member member=new Member("Jun",1999, Gender.male,165 ,2000,1400);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse("2023/09/30 00:00", formatter);
            WeightRecord weightRecord = new WeightRecord(dateTime, 60, member);
            member.addWeightRecord(weightRecord);

            LocalDateTime dateTime2 = LocalDateTime.parse("2023/10/30 00:00", formatter);
            WeightRecord weightRecord2 = new WeightRecord(dateTime2, 63, member);
            member.addWeightRecord(weightRecord2);

            LocalDateTime dateTime3 = LocalDateTime.parse("2023/12/31 00:00", formatter);
            WeightRecord weightRecord3 = new WeightRecord(dateTime3, 58, member);
            member.addWeightRecord(weightRecord3);

            LocalDateTime dateTime4 = LocalDateTime.parse("2023/01/30 00:00", formatter);
            WeightRecord weightRecord4 = new WeightRecord(dateTime4, 56, member);
            member.addWeightRecord(weightRecord4);

            em.persist(member);

            //팀 넣기
            LocalDateTime currentDateTime = LocalDateTime.now();
            Team team = new Team("첫번째 팀",member, 5,currentDateTime, ChallngeType.reduce);
            em.persist(team);

            //PersonalChallenge 넣기
            PersonalChallenge personalChallenge = new PersonalChallenge(LocalDate.now(), member);
            em.persist(personalChallenge);

            // EtcGoal 예시 추가
            EtcGoal goal1 = new EtcGoal("아침 8시 기상", false, personalChallenge);
            EtcGoal goal2 = new EtcGoal("출퇴근 자전거 타기", false, personalChallenge);
            personalChallenge.addEtcGoal(goal1);
            personalChallenge.addEtcGoal(goal2);
            em.persist(goal1);
            em.persist(goal2);

            // MealRecord 예시 추가
            MealRecord meal1 = new MealRecord("meal1.jpg", 500, LocalTime.now(), personalChallenge);
            MealRecord meal2 = new MealRecord("meal2.jpg", 600, LocalTime.now().minusHours(5), personalChallenge);
            personalChallenge.addMeal(meal1);
            personalChallenge.addMeal(meal2);
            em.persist(meal1);
            em.persist(meal2);
        }
        public void dbInit2() {
            Member member = new Member("glass", 1998, Gender.female, 160 ,1900,1200);
            em.persist(member);
        }
    }
}

