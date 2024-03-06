package hobbit.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="team")
public class Team {
    @Id @GeneratedValue
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<Member> members;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
