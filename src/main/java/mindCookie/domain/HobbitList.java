package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hobbitList")
@Getter
public class HobbitList {
    @Id
    @Column(name = "HOBBIT_LIST_ID")
    @GeneratedValue
    private Long id;

    private String goalName;
    private String primaryGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_HOBBIT_LIST_ID")
    private Member member;
    public void setMember(Member member) {
        this.member=member;
    }

    @OneToMany(mappedBy = "hobbitList", cascade = CascadeType.ALL)
    private List<DailyHobbitStatus> dailyHobbitStatus = new ArrayList<>();
    public void addDailyHobbitStatus(DailyHobbitStatus status) {
        dailyHobbitStatus.add(status);
        status.setHobbitList(this);
    }


}
