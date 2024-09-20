package mindCookie.dto.Hobbit;

import lombok.Data;

@Data
public class HobbitStatusDTO {
    private Long hobbitId;
    private String hobbit;
    private boolean isDone;

    public HobbitStatusDTO(Long hobbitId,String hobbit,boolean isDone) {
        this.hobbitId=hobbitId;
        this.hobbit = hobbit;
        this.isDone = isDone;
    }
}