package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.StateDTO;
import mindCookie.exception.MemberNotFoundException;
import mindCookie.exception.StateNotFoundException;
import mindCookie.repository.MemberRepository;
import mindCookie.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateService {
    private final StateRepository stateRepository;
    private final MemberRepository memberRepository;

    public State findTodayState(Long id) {
        LocalDate today = LocalDate.now();

        return stateRepository.findByDate(id, today)
                .orElseThrow(StateNotFoundException::new);
    }

    @Transactional
    public State updateOrCreateState(Long memberId, StateDTO getStateDTO) {
        LocalDate today = LocalDate.now();

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        // 오늘의 State 조회
        Optional<State> optionalState = stateRepository.findByDate(memberId, today);

        // State 가 존재하면 업데이트, 존재하지 않으면 생성
        State state;
        if (optionalState.isPresent()) {
            state = optionalState.get();
            state.setPositive(getStateDTO.getPositive());
            state.setNegative(getStateDTO.getNegative());
            state.setLifeSatisfaction(getStateDTO.getLifeSatisfaction());
            state.setPhysicalConnection(getStateDTO.getPhysicalCondition());
        } else {
            state = new State(today, getStateDTO.getPositive(), getStateDTO.getNegative(),
                    getStateDTO.getLifeSatisfaction(), getStateDTO.getPhysicalCondition());
//            state.setMember(member);
            member.addStates(state);
//            stateRepository.save(state);
        }

        return state;
    }
}
