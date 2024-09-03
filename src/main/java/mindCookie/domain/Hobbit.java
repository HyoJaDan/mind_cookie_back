package mindCookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hobbit")
@Getter
public class Hobbit {
    @Id
    @Column(name = "HOBBIT_ID")
    @GeneratedValue
    private Long id;
    private String goalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIMARY_HOBBIT_HOBBIT_ID")
    @JsonIgnore
    private PrimaryHobbit primaryHobbit;

    @OneToMany(mappedBy = "hobbit", cascade = CascadeType.ALL)
    private List<DailyHobbitStatus> dailyHobbitStatus = new ArrayList<>();

    public void setPrimaryHobbit(PrimaryHobbit primaryHobbit) {
        this.primaryHobbit=primaryHobbit;
    }
    public void addDailyHobbitStatus(DailyHobbitStatus status) {
        dailyHobbitStatus.add(status);
        status.setHobbitList(this);
    }

    public void addGoalName(String goalName) {
        this.goalName=goalName;
    }
}
