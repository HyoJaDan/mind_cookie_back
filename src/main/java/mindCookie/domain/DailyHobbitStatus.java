package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.sql.results.graph.Fetch;

import java.time.LocalDate;

@Entity
@Table(name = "dailyHobbitStatus")
@Getter
public class DailyHobbitStatus {

    @Id
    @Column(name = "DAILY_HOBBIT_LIST_ID")
    @GeneratedValue
    private Long id;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DAILY_HOBBIT_HOBBIT_ID")
    private Hobbit hobbit;

    public void setHobbitList(Hobbit hobbit) {
        this.hobbit = hobbit;
    }

    public void setDate(LocalDate now) {
        this.date=now;
    }
}
