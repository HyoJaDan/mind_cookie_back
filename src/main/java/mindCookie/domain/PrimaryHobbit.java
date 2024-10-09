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
    private String color;
    @Column(nullable = false, columnDefinition = "int default 0")
    private int numOfSucceed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_PRIMARY_HOBBIT_ID")
    @JsonIgnore
    private Member member;

    @OneToMany(mappedBy = "primaryHobbit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hobbit> hobbitList = new ArrayList<>();

    public PrimaryHobbit(String primaryGoal, String color, Member member) {
        this.primaryGoal = primaryGoal;
        this.color = color;
        this.numOfSucceed=0;
        addMember(member);
    }
    public PrimaryHobbit(){}

    public void plusSucceed(){
        this.numOfSucceed+=1;
    }
    public void minusSucceed(){
        this.numOfSucceed-=1;
    }
    private void addMember(Member member){
        this.member = member;
        if(member != null){
            member.addPrimaryHobbit(this);
        }
    }
    public void addHobbitList(Hobbit hobbit) {
        hobbitList.add(hobbit);
    }
}
