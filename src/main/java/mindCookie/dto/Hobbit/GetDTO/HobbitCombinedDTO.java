package mindCookie.dto.Hobbit.GetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HobbitCombinedDTO {
    private List<PrimaryHobbitDTO> primaryHobbits;
    private List<HobbitStatusByDateDTO> statusByDate;
    private List<PrimaryHobbitSuccessDTO> top3Success;
}