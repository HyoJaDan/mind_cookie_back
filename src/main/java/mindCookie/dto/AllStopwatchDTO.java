package mindCookie.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class AllStopwatchDTO {
    private String target;
    private List<DateTimeDTO> dateTimeList;
}
