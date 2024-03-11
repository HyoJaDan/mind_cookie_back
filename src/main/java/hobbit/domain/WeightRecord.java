package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weight_record")
@Getter
public class WeightRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private Integer weight;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Member_Id")
    private Member member;
    public void setMember(Member member) {
        this.member = member;
    }

    // 기본 생성자
    public WeightRecord() {
    }

    // 모든 필드를 인자로 받는 생성자
    public WeightRecord(LocalDateTime date, Integer weight, Member member) {
        this.date = date;
        this.weight = weight;
        this.member=member;
    }
}