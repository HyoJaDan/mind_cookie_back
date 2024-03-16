package hobbit.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Exercise {
    private double exerciseCalorie;
    private int durationInSeconds;
    private boolean isDone=false;

    public Exercise(){}

    public Exercise(int exerciseCalorie, boolean isDone) {
        this.exerciseCalorie = exerciseCalorie;
        this.isDone = isDone;
    }
}
