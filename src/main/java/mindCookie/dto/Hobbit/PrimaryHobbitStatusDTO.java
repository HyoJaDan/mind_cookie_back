package mindCookie.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrimaryHobbitStatusDTO {
    private Long primaryHobbitId;
    private String primaryHobbit;
    private String color;
    private List<HobbitStatusDTO> hobbitStatuses;

    public PrimaryHobbitStatusDTO(Long primaryHobbitId, String primaryHobbit, String color) {
        this.primaryHobbitId = primaryHobbitId;
        this.primaryHobbit = primaryHobbit;
        this.color = color;
        this.hobbitStatuses = new ArrayList<>();
    }

    public void addHobbitStatus(HobbitStatusDTO hobbitStatusDTO) {
        this.hobbitStatuses.add(hobbitStatusDTO);
    }
}
