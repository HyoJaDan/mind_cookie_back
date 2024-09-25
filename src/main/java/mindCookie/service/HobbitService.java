package mindCookie.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mindCookie.domain.*;
import mindCookie.dto.Hobbit.GetDTO.*;
import mindCookie.dto.Hobbit.HobbitStatusDTO;
import mindCookie.dto.Hobbit.PrimaryHobbitStatusDTO;
import mindCookie.dto.Hobbit.ToDoDTO;
import mindCookie.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HobbitService {
    private final PrimaryHobbitRepository primaryHobbitRepository;
    private final HobbitListRepository hobbitListRepository;
    private final DailyHobbitStatusRepository dailyHobbitStatusRepository;

    /**
     * 목표 추가
     * 만약 PrimaryHobbit 이 없으면, 새로 생성하고 새로운 goal 추가
     * 만약 primaryHobbit 이 있으면, 기존 primaryHobbit 에 goal 추가
     */
    @Transactional
    public PrimaryHobbitStatusDTO addHobbit(Member member, String primaryHobbitName, String goalName, String color) {

        // 상위 목표를 가져오거나 없으면 새로 추가
        Optional<PrimaryHobbit> primaryHobbitOptional = primaryHobbitRepository.findByNameAndMemberId(primaryHobbitName, member.getId());
        PrimaryHobbit primaryHobbit;

        if (primaryHobbitOptional.isPresent()) {
            primaryHobbit = primaryHobbitOptional.get();
        } else {
            primaryHobbit = new PrimaryHobbit(primaryHobbitName, color, member);
            primaryHobbitRepository.save(primaryHobbit);
        }

        // 새 목표 추가
        Hobbit hobbitList = new Hobbit(goalName, primaryHobbit);
        hobbitListRepository.save(hobbitList);

        // PrimaryHobbitStatusDTO로 반환
        PrimaryHobbitStatusDTO primaryHobbitStatusDTO = new PrimaryHobbitStatusDTO(
                primaryHobbit.getId(), primaryHobbit.getPrimaryGoal(), primaryHobbit.getColor()
        );

        HobbitStatusDTO hobbitStatusDTO = new HobbitStatusDTO(
                hobbitList.getId(), hobbitList.getGoalName(), false
        );
        primaryHobbitStatusDTO.addHobbitStatus(hobbitStatusDTO);

        return primaryHobbitStatusDTO;
    }

    /**
     * v1
     * 모든 hobbitList 가져오기
     * 없을 경우 빈 배열 반환
     * 있으면 PrimaryHobbit 을 멤버 아이디로 찾고, HobbitList 를 fetch join 으로, 해당 데이터를
     * DTO 형식으로 만다.
     */
    public List<PrimaryHobbitStatusDTO> getTodayHobbitStatus(Member member, LocalDate date) {
        Optional<List<PrimaryHobbit>> primaryHobbits = primaryHobbitRepository.findByMemberId(member.getId());
        List<PrimaryHobbitStatusDTO> primaryHobbitStatusDTOs = new ArrayList<>();

        if (primaryHobbits.isEmpty()) return primaryHobbitStatusDTOs;

        for (PrimaryHobbit primaryHobbit : primaryHobbits.get()) {
            PrimaryHobbitStatusDTO primaryHobbitStatusDTO = new PrimaryHobbitStatusDTO(
                    primaryHobbit.getId(), primaryHobbit.getPrimaryGoal(), primaryHobbit.getColor());

            for (Hobbit hobbit : primaryHobbit.getHobbitList()) {
                boolean isDone = dailyHobbitStatusRepository.existsByHobbitListAndDate(hobbit, date);
                HobbitStatusDTO hobbitStatusDTO = new HobbitStatusDTO(
                        hobbit.getId(), hobbit.getGoalName(), isDone);

                primaryHobbitStatusDTO.addHobbitStatus(hobbitStatusDTO);
            }

            primaryHobbitStatusDTOs.add(primaryHobbitStatusDTO);
        }

        return primaryHobbitStatusDTOs;
    }

    // v-2
    public List<ToDoDTO> getHobbitStatusForNext7Days(Member member) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6); // 7일간의 데이터 (오늘 포함)

        // Member ID로 모든 PrimaryHobbit 가져오기
        List<PrimaryHobbit> primaryHobbits = primaryHobbitRepository.findAllByMember(member);
        List<ToDoDTO> hobbitStatusByDateList = new ArrayList<>();

        // 날짜별로 데이터를 저장할 리스트
        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            List<PrimaryHobbitStatusDTO> primaryHobbitStatusDTOs = new ArrayList<>();

            // 각 날짜별 PrimaryHobbit 데이터를 가져옴
            for (PrimaryHobbit primaryHobbit : primaryHobbits) {
                PrimaryHobbitStatusDTO primaryHobbitStatusDTO = new PrimaryHobbitStatusDTO(
                        primaryHobbit.getId(), primaryHobbit.getPrimaryGoal(), primaryHobbit.getColor());

                // 해당 PrimaryHobbit와 연관된 Hobbit과 DailyHobbitStatus 가져오기
                for (Hobbit hobbit : primaryHobbit.getHobbitList()) {
                    boolean isDone = dailyHobbitStatusRepository.existsByHobbitListAndDate(hobbit, date);

                    HobbitStatusDTO hobbitStatusDTO = new HobbitStatusDTO(
                            hobbit.getId(), hobbit.getGoalName(), isDone);

                    primaryHobbitStatusDTO.addHobbitStatus(hobbitStatusDTO);
                }

                primaryHobbitStatusDTOs.add(primaryHobbitStatusDTO);
            }

            // 날짜별 DTO 생성 및 리스트에 추가
            hobbitStatusByDateList.add(new ToDoDTO(date, primaryHobbitStatusDTOs));
        }

        return hobbitStatusByDateList;
    }
    // v-3 기본 목표 정보와 날짜별 완료 상태를 함께 가져오기
    public HobbitCombinedDTO getHobbitCombinedData(Member member) {
        // 기본 목표 정보 가져오기
        List<PrimaryHobbit> primaryHobbits = primaryHobbitRepository.findAllByMember(member);
        List<PrimaryHobbitDTO> primaryHobbitDTOs = new ArrayList<>();
        List<PrimaryHobbitSuccessDTO> successList = new ArrayList<>();

        // 목표와 성공 횟수 계산
        for (PrimaryHobbit primaryHobbit : primaryHobbits) {
            PrimaryHobbitDTO primaryHobbitDTO = new PrimaryHobbitDTO(
                    primaryHobbit.getId(), primaryHobbit.getPrimaryGoal(), primaryHobbit.getColor());

            for (Hobbit hobbit : primaryHobbit.getHobbitList()) {
                HobbitDTO hobbitDTO = new HobbitDTO(hobbit.getId(), hobbit.getGoalName());
                primaryHobbitDTO.addHobbit(hobbitDTO);
            }

            primaryHobbitDTOs.add(primaryHobbitDTO);
            successList.add(new PrimaryHobbitSuccessDTO(primaryHobbit.getPrimaryGoal(), primaryHobbit.getNumOfSucceed()));
        }

        // 날짜별 완료 상태 가져오기
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6); // 7일간의 데이터 (오늘 포함)

        // 모든 PrimaryHobbit에 속한 Hobbit을 가져오기
        List<Hobbit> allHobbits = primaryHobbits.stream()
                .flatMap(ph -> ph.getHobbitList().stream())
                .collect(Collectors.toList());

        // 모든 날짜와 hobbit의 완료 상태를 한 번에 가져오기 위한 Map 생성
        Map<LocalDate, Map<Long, Boolean>> hobbitStatusMap = dailyHobbitStatusRepository.getHobbitStatusMap(allHobbits, startDate, today);

        List<HobbitStatusByDateDTO> hobbitStatusByDateList = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            List<Boolean> hobbitStatusList = new ArrayList<>();

            for (PrimaryHobbit primaryHobbit : primaryHobbits) {
                for (Hobbit hobbit : primaryHobbit.getHobbitList()) {
                    boolean isDone = hobbitStatusMap.getOrDefault(date, new HashMap<>())
                            .getOrDefault(hobbit.getId(), false);
                    hobbitStatusList.add(isDone);  // boolean 값만 추가
                }
            }

            hobbitStatusByDateList.add(new HobbitStatusByDateDTO(date, hobbitStatusList));
        }

        // 상위 3개의 성공 횟수를 가진 목표 정렬
        List<PrimaryHobbitSuccessDTO> top3SuccessList = successList.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getNumOfSucceed(), s1.getNumOfSucceed())) // 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .collect(Collectors.toList());

        // 두 가지 데이터를 결합하여 반환
        return new HobbitCombinedDTO(primaryHobbitDTOs, hobbitStatusByDateList, top3SuccessList);
    }


    @Transactional
    public boolean updateTodayHobbitStatus(Long memberId, Long primaryHobbitId, Long hobbitListId, LocalDate date) {
        Hobbit hobbitList = hobbitListRepository.findByMemberIdAndPrimaryHobbitIdAndHobbitListId(memberId, primaryHobbitId, hobbitListId);
        PrimaryHobbit primaryHobbit = hobbitList.getPrimaryHobbit();

        // 정확한 데이터가 없을 경우 예외 처리
        if (hobbitList == null) {
            throw new IllegalArgumentException("해당 사용자의 목표가 존재하지 않습니다.");
        }

        // 오늘 날짜의 DailyHobbitStatus가 존재하는지 확인
        DailyHobbitStatus existingStatus = dailyHobbitStatusRepository.findByHobbitListAndDate(hobbitList, date);

        if (existingStatus != null) {
            // 존재하면 삭제하고 isDone = false 반환
            dailyHobbitStatusRepository.delete(existingStatus);
            primaryHobbit.minusSucceed();
            return false;
        } else {
            // 존재하지 않으면 새로운 DailyHobbitStatus 생성 후 저장, isDone = true 반환
            DailyHobbitStatus newStatus = new DailyHobbitStatus(date, hobbitList);
            dailyHobbitStatusRepository.save(newStatus);
            primaryHobbit.plusSucceed();
            return true;
        }
    }
}
