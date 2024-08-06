package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "event")
@Getter
public class Event {
    @Id
    @GeneratedValue
    @Column(name="EVENT_ID")
    private Long id;

    private LocalDate date;
    private List<String> participants;
    private String which_activity;
    private String emotion;
    private byte emotion_rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_EVENT_ID")
    private Member member;

    public void setMember(Member member) {
        this.member=member;
    }
}
