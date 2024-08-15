package mindCookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "stopwatch")
@Getter
public class Stopwatch {
    @Id
    @Column(name = "STOPWATCH_ID")
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private LocalTime time;
    public void updateTime(LocalTime time) {
        this.time=time;
    }
    private String target;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_STOPWATCH_ID")
    @JsonIgnore
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    protected Stopwatch(){}
    public Stopwatch(LocalDate date, LocalTime time, String target) {
        this.date = date;
        this.time = time;
        this.target = target;
    }
}
