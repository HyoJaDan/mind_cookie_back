package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.EventInfoDTO;
import mindCookie.exception.MemberNotFoundException;
import mindCookie.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
    public EventInfoDTO getMemberEventInfo(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        List<String> eventParticipants = findMember.getEvent_participants();
        List<String> eventActivities = findMember.getEvent_activities();
        List<String> eventEmotions = findMember.getEvent_emotions();
        if(eventParticipants==null)
            eventParticipants = new ArrayList<>();
        if(eventActivities==null)
            eventActivities = new ArrayList<>();
        if(eventEmotions==null)
            eventEmotions = new ArrayList<>();

        return new EventInfoDTO(eventParticipants, eventActivities, eventEmotions);
    }
}
