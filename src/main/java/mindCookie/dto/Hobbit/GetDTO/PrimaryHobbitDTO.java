package mindCookie.dto.Hobbit.GetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PrimaryHobbitDTO {
    private Long primaryHobbitId;
    private String primaryHobbit;
    private String color;
    private List<HobbitDTO> hobbitStatuses = new ArrayList<>();

    public void addHobbit(HobbitDTO hobbit) {
        hobbitStatuses.add(hobbit);
    }
    public PrimaryHobbitDTO(Long primaryHobbitId, String primaryHobbit, String color) {
        this.primaryHobbitId = primaryHobbitId;
        this.primaryHobbit = primaryHobbit;
        this.color = color;
    }
}