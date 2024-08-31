package mindCookie.dto;

import lombok.Data;
import mindCookie.domain.Member;

import java.util.List;

@Data
public class MemberDTO {
    private List<String> event_participants;
    private List<String> event_activities;
    private List<String> event_emotions;
    private List<String> stopwatch_target;
    public MemberDTO(Member member){
        this.event_participants = member.getEvent_participants();
        this.event_activities = member.getEvent_activities();
        this.event_emotions = member.getEvent_emotions();
        this.stopwatch_target = member.getStopwatch_target();
    }
}
