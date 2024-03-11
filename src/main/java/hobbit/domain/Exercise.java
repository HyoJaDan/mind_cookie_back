package hobbit.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Exercise {
    private int exerciseCalorie;
    private boolean isDone=false;

    public Exercise(){}

    public Exercise(int exerciseCalorie, boolean isDone) {
        this.exerciseCalorie = exerciseCalorie;
        this.isDone = isDone;
    }
}
