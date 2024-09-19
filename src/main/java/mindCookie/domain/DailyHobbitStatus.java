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

    public DailyHobbitStatus(LocalDate date, Hobbit hobbit) {
        this.date = date;
        addHobbitList(hobbit);
    }
    public DailyHobbitStatus(){}
    private void addHobbitList(Hobbit hobbit){
        this.hobbit=hobbit;
        if(hobbit != null)
            hobbit.addDailyHobbitStatus(this);
    }
}
