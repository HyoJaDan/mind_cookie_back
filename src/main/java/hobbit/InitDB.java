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
        }
        public void dbInit2() {
            Member member = new Member("glass", 1998, Gender.female, 160 ,1900,1200);
            em.persist(member);

            LocalDate currentDateTime = LocalDate.now();
            Team team = new Team("첫번째 팀",member, 5,currentDateTime, ChallngeType.reduce);
            em.persist(team);
        }
    }
}

