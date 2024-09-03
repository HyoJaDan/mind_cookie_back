package mindCookie.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mindCookie.domain.*;
import mindCookie.dto.HobbitStatusDTO;
import mindCookie.dto.PrimaryHobbitStatusDTO;
import mindCookie.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean addHobbit(Member member, String primaryHobbitName, String goalName) {

        // 상위 목표를 가져오거나 없으면 새로 추가
        Optional<PrimaryHobbit> primaryHobbitOptional = primaryHobbitRepository.findByNameAndMemberId(primaryHobbitName, member.getId());
        PrimaryHobbit primaryHobbit;

        if (primaryHobbitOptional.isPresent()) {
            primaryHobbit = primaryHobbitOptional.get();
        } else {
            primaryHobbit = new PrimaryHobbit();
            primaryHobbit.addPrimaryGoal(primaryHobbitName);
            primaryHobbit.setMember(member);
            primaryHobbitRepository.save(primaryHobbit);
        }

        // 새 목표 추가
        Hobbit hobbitList = new Hobbit();
        hobbitList.setPrimaryHobbit(primaryHobbit);
        hobbitList.addGoalName(goalName);

        return hobbitListRepository.save(hobbitList);
    }

    /**
     * 모든 hobbitList 가져오기
     * 없을 경우 빈 배열 반환
     * 있으면 PrimaryHobbit 을 멤버 아이디로 찾고, HobbitList 를 fetch join 으로, 해당 데이터를
     * DTO 형식으로 만다.
     */
    public List<PrimaryHobbitStatusDTO> getTodayHobbitStatus(Member member) {
        Optional<List<PrimaryHobbit>> primaryHobbits = primaryHobbitRepository.findByMemberId(member.getId());
        List<PrimaryHobbitStatusDTO> primaryHobbitStatusDTOs = new ArrayList<>();

        if (primaryHobbits.isEmpty()) return primaryHobbitStatusDTOs;

        for (PrimaryHobbit primaryHobbit : primaryHobbits.get()) {
            PrimaryHobbitStatusDTO primaryHobbitStatusDTO = new PrimaryHobbitStatusDTO(
                    primaryHobbit.getId(), primaryHobbit.getPrimaryGoal());

            for (Hobbit hobbit : primaryHobbit.getHobbitList()) {
                boolean isDone = dailyHobbitStatusRepository.existsByHobbitListAndDate(hobbit, LocalDate.now());
                HobbitStatusDTO hobbitStatusDTO = new HobbitStatusDTO(
                        hobbit.getId(), hobbit.getGoalName(), isDone);

                primaryHobbitStatusDTO.addHobbitStatus(hobbitStatusDTO);
            }

            primaryHobbitStatusDTOs.add(primaryHobbitStatusDTO);
        }

        return primaryHobbitStatusDTOs;
    }

    @Transactional
    public boolean updateTodayHobbitStatus(Long memberId, Long primaryHobbitId, Long hobbitListId) {
        // MemberId를 사용하여 HobbitList 엔티티를 가져오기
        Hobbit hobbitList = hobbitListRepository.findByMemberIdAndPrimaryHobbitIdAndHobbitListId(memberId, primaryHobbitId, hobbitListId);

        // 정확한 데이터가 없을 경우 예외 처리
        if (hobbitList == null) {
            throw new IllegalArgumentException("해당 사용자의 목표가 존재하지 않습니다.");
        }

        // 오늘 날짜의 DailyHobbitStatus가 존재하는지 확인
        DailyHobbitStatus existingStatus = dailyHobbitStatusRepository.findByHobbitListAndDate(hobbitList, LocalDate.now());

        if (existingStatus != null) {
            // 존재하면 삭제하고 isDone = false 반환
            dailyHobbitStatusRepository.delete(existingStatus);
            return false;
        } else {
            // 존재하지 않으면 새로운 DailyHobbitStatus 생성 후 저장, isDone = true 반환
            DailyHobbitStatus newStatus = new DailyHobbitStatus();
            newStatus.setDate(LocalDate.now());
            newStatus.setHobbitList(hobbitList);
            dailyHobbitStatusRepository.save(newStatus);
            return true;
        }
    }

}
