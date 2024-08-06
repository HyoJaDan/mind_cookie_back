package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;

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
    private byte negative;
    private byte lifeSatisfaction;
    private byte physicalConnection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_STATE_ID")
    private Member member;

    public void setMember(Member member) {
        this.member=member;
    }
}
