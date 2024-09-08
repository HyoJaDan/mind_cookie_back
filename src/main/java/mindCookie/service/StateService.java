package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.StateDTO;
import mindCookie.exception.MemberNotFoundException;
import mindCookie.exception.StateNotFoundException;
import mindCookie.repository.MemberRepository;
import mindCookie.repository.StateRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateService {
    private final StateRepository stateRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public State findState(Long id, LocalDate date) {
        Optional<State> state = stateRepository.findByDate(id, date);
        if(state.isPresent()){
            return state.get();
        }
        return new State(LocalDate.now(), (byte) 50,(byte) 50,(byte) 50,(byte) 50);
    }

    @Transactional
    public void updateOrCreateState(Member member, StateDTO getStateDTO, LocalDate date) {
        Optional<State> optionalState = stateRepository.findByDate(member.getId(), date);

        State state;
        if (optionalState.isPresent()) {
            state = optionalState.get();
            state.setPositive(getStateDTO.getPositive());
            state.setNegative(getStateDTO.getNegative());
            state.setLifeSatisfaction(getStateDTO.getLifeSatisfaction());
            state.setPhysicalConnection(getStateDTO.getPhysicalCondition());
        } else {
            state = new State(date, getStateDTO.getPositive(), getStateDTO.getNegative(),
                    getStateDTO.getLifeSatisfaction(), getStateDTO.getPhysicalCondition());
            member.addStates(state);
        }
    }
}
