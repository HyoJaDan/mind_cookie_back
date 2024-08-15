package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.Stopwatch;
import mindCookie.dto.StopwatchDTO;
import mindCookie.repository.MemberRepository;
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
    private final MemberService memberService;

    public List<StopwatchDTO> findTodayStopwatchList(Long id) {
        LocalDate today = LocalDate.now();
        Optional<List<Stopwatch>> todayStopwatchList = stopwatchRepository.findByDate(id, today);
        List<StopwatchDTO> returnValue = new ArrayList<>();

        // Member 엔티티에서 stopwatch_target 리스트를 가져옴
        Member findMember = memberService.getMemberById(id);
        List<String> stopwatchTargets = findMember.getStopwatch_target();

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
    public void addStopwatchTarget(Long id, String newTarget) {
        Member findMember = memberService.getMemberById(id);
        findMember.addStopwatch_target(newTarget);
    }

    @Transactional
    public void removeStopwatchTarget(Long id, String targetToRemove) {
        Member findMember = memberService.getMemberById(id);
        findMember.removeStopwatch_target(targetToRemove);
    }
}
