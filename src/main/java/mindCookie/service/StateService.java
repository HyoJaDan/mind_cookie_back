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
    public void updateOrCreateState(Long memberId, StateDTO getStateDTO) {
        LocalDate today = LocalDate.now();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Optional<State> optionalState = stateRepository.findByDate(memberId, today);

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
            member.addStates(state);
        }
    }
}
