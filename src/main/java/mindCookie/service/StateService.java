package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.AllStateDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // 사용자의 모든 State 정보를 가져오는 서비스

    public List<AllStateDTO> getAllStates(Long memberId) {
        List<State> states = stateRepository.findAllStatesByMemberId(memberId);

        // State -> StateDTO 변환
        return states.stream()
                .map(state -> new AllStateDTO(
                        state.getDate(),
                        state.getPositive(),
                        state.getNegative(),
                        state.getLifeSatisfaction(),
                        state.getPhysicalConnection()
                ))
                .collect(Collectors.toList());
    }
}
