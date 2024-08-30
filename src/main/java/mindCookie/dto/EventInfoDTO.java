package mindCookie.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventInfoDTO {
    private List<String> event_participants;
    private List<String> event_activities;
    private List<String> event_emotions;

    public EventInfoDTO(List<String> event_participants,
                        List<String> event_activities,
                        List<String> event_emotions){
        this.event_participants=event_participants;
        this.event_activities=event_activities;
        this.event_emotions=event_emotions;
    }
}
