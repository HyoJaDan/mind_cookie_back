package mindCookie.dto.Hobbit.GetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class HobbitStatusByDateDTO {
    private LocalDate date;
    private List<HobbitStatusDTO2> hobbitStatus;
}