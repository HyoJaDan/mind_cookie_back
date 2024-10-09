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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIMARY_HOBBIT_HOBBIT_ID")
    @JsonIgnore
    private PrimaryHobbit primaryHobbit;

    @OneToMany(mappedBy = "hobbit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyHobbitStatus> dailyHobbitStatus = new ArrayList<>();

    public Hobbit(String goalName, PrimaryHobbit primaryHobbit) {
        this.goalName = goalName;
        addPrimaryHobbit(primaryHobbit);
    }
    public Hobbit(){}
    private void addPrimaryHobbit(PrimaryHobbit primaryHobbit){
        this.primaryHobbit = primaryHobbit;
        if(primaryHobbit != null)
            primaryHobbit.addHobbitList(this);
    }
    public void removePrimaryHobbit(){
        this.primaryHobbit=null;
    }
    public void addDailyHobbitStatus(DailyHobbitStatus dailyHobbitStatus){
        this.dailyHobbitStatus.add(dailyHobbitStatus);
    }
}
