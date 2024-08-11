package mindCookie.dto;

import lombok.Data;

@Data
public class StateDTO {
    private byte positive;
    private byte negative;
    private byte lifeSatisfaction;
    private byte physicalCondition;

    public StateDTO(byte positive, byte negative, byte lifeSatisfaction, byte physicalConnection) {
        this.positive=positive;
        this.negative=negative;
        this.lifeSatisfaction=lifeSatisfaction;
        this.physicalCondition=physicalConnection;
    }
}
