package mindCookie.dto.Hobbit.GetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HobbitStatusDTO {
    private Long hobbitId;
    private boolean isDone;
}