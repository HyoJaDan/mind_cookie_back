package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.Stopwatch;
import mindCookie.dto.StopwatchDTO;
import mindCookie.repository.StopwatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StopwatchService {
    private final StopwatchRepository stopwatchRepository;

    public List<StopwatchDTO> findTodayStopwatchList(Member member) {
        LocalDate today = LocalDate.now();
        Optional<List<Stopwatch>> todayStopwatchList = stopwatchRepository.findByDate(member.getId(), today);
        List<StopwatchDTO> returnValue = new ArrayList<>();

        // Member 엔티티에서 stopwatch_target 리스트를 가져옴
        List<String> stopwatchTargets = member.getStopwatch_target();

        // 모든 stopwatch_target에 대해 DTO를 생성
        for (String target : stopwatchTargets) {
            // 해당 target이 오늘의 Stopwatch 리스트에 있는지 확인
            boolean isTargetRecordedToday = todayStopwatchList.isPresent() &&
                    todayStopwatchList.get().stream().anyMatch(stopwatch -> stopwatch.getTarget().equals(target));

            if (isTargetRecordedToday) {
                // 오늘 기록된 target의 DTO를 생성
                todayStopwatchList.get().stream()
                        .filter(stopwatch -> stopwatch.getTarget().equals(target))
                        .findFirst()
                        .ifPresent(stopwatch -> returnValue.add(new StopwatchDTO(stopwatch.getTarget(), stopwatch.getTime())));
            } else {
                // 오늘 기록되지 않은 target의 경우, time = 0으로 설정
                returnValue.add(new StopwatchDTO(target, LocalTime.ofSecondOfDay(0)));
            }
        }
        return returnValue;
    }

    @Transactional
    public void updateStopwatchTime(Member member, String target, LocalTime time) {
        LocalDate today = LocalDate.now();

        // 오늘 날짜와 특정 target에 해당하는 스탑워치를 가져옴
        Optional<List<Stopwatch>> todayStopwatchList = stopwatchRepository.findByDate(member.getId(), today);

        if (todayStopwatchList.isPresent()) {
            Stopwatch stopwatch = todayStopwatchList.get().stream()
                    .filter(s -> s.getTarget().equals(target))
                    .findFirst()
                    .orElse(null);

            if (stopwatch != null) {
                // 시간 업데이트
                stopwatch.updateTime(time);
            } else {
                // 만약 오늘의 타겟이 없으면 새로운 스탑워치 엔티티를 생성
                Stopwatch newStopwatch = new Stopwatch(today, time, target);
                member.addStopwatches(newStopwatch);
            }
        } else {
            // 오늘 날짜의 기록이 아예 없을 경우, 새로운 스탑워치 엔티티를 생성
            Stopwatch newStopwatch = new Stopwatch(today, time, target);
            member.addStopwatches(newStopwatch);
        }
    }

    @Transactional
    public void addStopwatchTarget(Member member, String newTarget) {
        member.addStopwatch_target(newTarget);
    }

    @Transactional
    public void removeStopwatchTarget(Member member, String targetToRemove) {
        member.removeStopwatch_target(targetToRemove);
    }
}
