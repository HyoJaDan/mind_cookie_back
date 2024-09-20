package mindCookie.dto.Hobbit.GetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PrimaryHobbitDTO {
    private Long primaryHobbitId;
    private String primaryGoal;
    private String color;
    private List<HobbitDTO> hobbits = new ArrayList<>();

    public void addHobbit(HobbitDTO hobbit) {
        hobbits.add(hobbit);
    }
    public PrimaryHobbitDTO(Long primaryHobbitId, String primaryGoal, String color) {
        this.primaryHobbitId = primaryHobbitId;
        this.primaryGoal = primaryGoal;
        this.color = color;
    }
}