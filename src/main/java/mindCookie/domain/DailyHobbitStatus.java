package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.sql.results.graph.Fetch;

import java.time.LocalDate;

@Entity
@Table(name = "dailyHobbitStatue")
@Getter
public class DailyHobbitStatus {

    @Id
    @Column(name = "DAILY_HOBBIT_LIST_ID")
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private Boolean isDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="HOBBIT_LIST_ID")
    private HobbitList hobbitList;

    public void setHobbitList(HobbitList hobbitList) {
        this.hobbitList = hobbitList;
    }
}
