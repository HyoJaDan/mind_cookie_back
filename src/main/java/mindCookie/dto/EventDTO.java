package mindCookie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class EventDTO {
    private LocalDate date;
    private List<String> participants;
    private String whichActivity;
    private String emotion;
    private byte emotionRate;
}