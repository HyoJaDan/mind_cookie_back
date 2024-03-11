package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "etc_goal")
@Getter
public class EtcGoal {
    @Id @GeneratedValue
    private Long id;

    private String goalName;
    private boolean isDone=false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etc_to_personalChallenge")
    private PersonalChallenge personalChallenge;

    public void setPersonalChallenge(PersonalChallenge personalChallenge) {
        this.personalChallenge = personalChallenge;
    }

    public EtcGoal() {
    }

    public EtcGoal(String goalName, boolean isDone, PersonalChallenge personalChallenge) {
        this.goalName = goalName;
        this.isDone = isDone;
        this.personalChallenge = personalChallenge;
    }
}
