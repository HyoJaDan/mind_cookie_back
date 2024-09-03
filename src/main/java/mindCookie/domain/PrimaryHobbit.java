package mindCookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "primary_hobbit")
@Getter
public class PrimaryHobbit {
    @Id
    @Column(name = "PRIMARY_HOBBIT_ID")
    @GeneratedValue
    private Long id;
    private String primaryGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_PRIMARY_HOBBIT_ID")
    @JsonIgnore
    private Member member;

    @OneToMany(mappedBy = "primaryHobbit", cascade = CascadeType.ALL)
    private List<Hobbit> hobbitList = new ArrayList<>();
    public void setMember(Member member) {
        this.member=member;
    }

    public void addHobbitList(Hobbit hobbit) {
        hobbitList.add(hobbit);
        hobbit.setPrimaryHobbit(this);
    }

    public void addPrimaryGoal(String primaryHobbitName) {
        this.primaryGoal=primaryHobbitName;
    }
}
