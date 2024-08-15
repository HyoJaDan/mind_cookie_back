package mindCookie.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class StopwatchDTO {
    private LocalTime time;
    private String target;


    public StopwatchDTO(String target,LocalTime time) {
        this.target=target;
        this.time=time;
    }
    public StopwatchDTO(String target) {
        this(target, LocalTime.ofSecondOfDay(0));
    }
}
