package mindCookie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DateTimeDTO {
    private LocalDate date;
    private LocalTime time;
}