package mindCookie;

import mindCookie.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    // @PostConstruct : 가장 편리하게 초기화와를 할수 있는 방법
    // @PreDestroy : 반대로 종료를 할수 있는 방법
    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1() {
            /**
             *     private List<String> event_participants;
             *     private List<String> event_activities;
             *     private List<String> event_emotions;
             *     private List<String> stopwatch_target;
             */

            List<String> event_participants = new ArrayList<>();
            event_participants.add("성호");
            event_participants.add("현우");

            List<String> event_activities = new ArrayList<>();
            event_activities.add("놀기");
            event_activities.add("먹기");

            List<String> event_emotions = new ArrayList<>();
            event_emotions.add("행복");
            event_emotions.add("슬픔");

            List<String> stopwatch_target = new ArrayList<>();
            stopwatch_target.add("운동");
            stopwatch_target.add("책읽기");

            Member member = new Member(event_participants,event_activities,event_emotions,stopwatch_target);

            em.persist(member);
        }

    }
}

