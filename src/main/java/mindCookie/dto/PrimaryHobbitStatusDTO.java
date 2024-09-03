package mindCookie.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrimaryHobbitStatusDTO {
    private Long primaryHobbitId;
    private String primaryHobbit;
    private List<HobbitStatusDTO> hobbitStatuses;

    public PrimaryHobbitStatusDTO(Long primaryHobbitId, String primaryHobbit) {
        this.primaryHobbitId = primaryHobbitId;
        this.primaryHobbit = primaryHobbit;
        this.hobbitStatuses = new ArrayList<>();
    }

    public void addHobbitStatus(HobbitStatusDTO hobbitStatusDTO) {
        this.hobbitStatuses.add(hobbitStatusDTO);
    }
}
