package mindCookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import mindCookie.global.uilts.Util;

import java.time.LocalDate;

@Entity
@Table(name="state")
@Getter
public class State {
    @Id
    @GeneratedValue
    @Column(name = "STATE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_STATE_ID")
    @JsonIgnore
    private Member member;

    private LocalDate date;
    private byte positive;
    private byte negative;
    private byte lifeSatisfaction;
    private byte physicalConnection;
    public void setPositive(byte positive) {
        byte Rate = Util.validateRate(positive);
        this.positive = Rate;
    }
    public void setNegative(byte negative) {
        byte Rate = Util.validateRate(negative);
        this.negative = Rate;
    }
    public void setLifeSatisfaction(byte lifeSatisfaction) {
        byte Rate = Util.validateRate(lifeSatisfaction);
        this.lifeSatisfaction = Rate;
    }
    public void setPhysicalConnection(byte physicalConnection) {
        byte Rate = Util.validateRate(physicalConnection);
        this.physicalConnection = Rate;
    }
    public void setMember(Member member) {
        this.member=member;
    }
    protected  State(){}
    public State(LocalDate date, byte positive, byte negative, byte lifeSatisfaction, byte physicalConnection) {
        this.date = date;
        setPositive(positive);
        setNegative(negative);
        setLifeSatisfaction(lifeSatisfaction);
        setPhysicalConnection(physicalConnection);
    }
}
