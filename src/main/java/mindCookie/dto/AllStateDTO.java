package mindCookie.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class AllStateDTO {
    private LocalDate date;
    private byte positive;
    private byte negative;
    private byte lifeSatisfaction;
    private byte physicalConnection;
}