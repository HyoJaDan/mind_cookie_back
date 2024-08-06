package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "stopwatch")
@Getter
public class Stopwatch {

    @Id
    @Column(name = "STOPWATCH_ID")
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private LocalDateTime time;
    private String target;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_STOPWATCH_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
