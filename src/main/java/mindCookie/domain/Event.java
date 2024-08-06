package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import mindCookie.Util;

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
    private byte emotionRate;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_EVENT_ID")
    private Member member;

    public void setMember(Member member) {
        this.member=member;
    }

    public void setEmotionRate(byte emotionRate) {
        byte Rate = Util.validateRate(emotionRate);

        this.emotionRate = Rate;
    }
    protected  Event(){}
    public Event(LocalDate date, List<String> participants, String which_activity, String emotion, byte emotionRate) {
        this.date = date;
        this.participants = participants;
        this.which_activity = which_activity;
        this.emotion = emotion;
        setEmotionRate(emotionRate);
    }
}
