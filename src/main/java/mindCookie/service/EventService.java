package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Event;
import mindCookie.domain.Member;
import mindCookie.dto.EventDTO;
import mindCookie.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final MemberService memberService;
    public List<EventDTO> getEventListByDate(Long memberId) {
        LocalDate today = LocalDate.now();
        Optional<List<Event>> findEvent = eventRepository.findEventsByMemberAndDate(memberId, today);

        return findEvent
                .map(events -> events.stream()
                        .map(event -> new EventDTO(
                                event.getDate(),
                                event.getParticipants(),
                                event.getWhich_activity(),
                                event.getEmotion(),
                                event.getEmotionRate()
                        ))
                        .collect(Collectors.toList())
                )
                .orElseGet(List::of);
    }

    @Transactional
    public void addEvent(Long memberId, EventDTO eventDTO) {
        Member findMember = memberService.getMemberById(memberId);

        addIfAbsent(findMember.getEvent_participants(),eventDTO.getParticipants());
        addIfAbsent(findMember.getEvent_activities(), eventDTO.getWhichActivity());
        addIfAbsent(findMember.getEvent_emotions(), eventDTO.getEmotion());

        Event event = new Event(eventDTO.getDate(),eventDTO.getParticipants(),eventDTO.getWhichActivity(),eventDTO.getEmotion(),eventDTO.getEmotionRate());
        findMember.addEvents(event);
    }

    private void addIfAbsent(List<String> eventParticipants, List<String> participants) {
        for(String person : participants){
            if(!eventParticipants.contains(person))
                eventParticipants.add(person);
        }
    }

    private void addIfAbsent(List<String> memberList, String dtoValue) {
        if (!memberList.contains(dtoValue)) {
            memberList.add(dtoValue);
        }
    }
}
