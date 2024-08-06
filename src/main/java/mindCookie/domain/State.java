package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import mindCookie.Util;

import java.time.LocalDate;

@Entity
@Table(name="state")
@Getter
public class State {
    @Id
    @GeneratedValue
    @Column(name = "STATE_ID")
    private Long id;

    private LocalDate date;
    private byte positive;

    public void setPositive(byte positive) {
        byte Rate = Util.validateRate(positive);

        this.positive = Rate;
    }

    private byte negative;
    public void setNegative(byte negative) {
        byte Rate = Util.validateRate(negative);
        this.negative = Rate;
    }

    private byte lifeSatisfaction;
    public void setLifeSatisfaction(byte lifeSatisfaction) {
        byte Rate = Util.validateRate(lifeSatisfaction);
        this.lifeSatisfaction = Rate;
    }

    private byte physicalConnection;
    public void setPhysicalConnection(byte physicalConnection) {
        byte Rate = Util.validateRate(physicalConnection);
        this.physicalConnection = Rate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_STATE_ID")
    private Member member;

    public void setMember(Member member) {
        this.member=member;
    }

    public State(LocalDate date, byte positive, byte negative, byte lifeSatisfaction, byte physicalConnection) {
        this.date = date;
        this.positive = positive;
        this.negative = negative;
        this.lifeSatisfaction = lifeSatisfaction;
        this.physicalConnection = physicalConnection;
    }
}
