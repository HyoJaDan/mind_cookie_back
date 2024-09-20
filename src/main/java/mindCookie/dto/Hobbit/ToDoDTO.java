package mindCookie.dto.Hobbit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class HobbitByDateDTO {
    private LocalDate date;
    private List<PrimaryHobbitStatusDTO> primaryHobbitStatusDTOs;
}
