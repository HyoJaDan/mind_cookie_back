package mindCookie.dto.Hobbit;

@Getter
@AllArgsConstructor
public class HobbitCombinedDTO {
    private List<PrimaryHobbitDTO> primaryHobbits;
    private List<HobbitStatusByDateDTO> statusByDate;
}