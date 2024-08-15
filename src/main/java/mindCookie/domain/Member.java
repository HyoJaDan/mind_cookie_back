package mindCookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue
    private Long id;

    private List<String> event_participants;
    private List<String> event_activities;
    private List<String> event_emotions;
    private List<String> stopwatch_target;

    public void addStopwatch_target(String stopwatch_target) {
        this.stopwatch_target.add(stopwatch_target);
    }
    public void removeStopwatch_target(String targetToRemove){
        this.stopwatch_target.remove(targetToRemove);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stopwatch> stopwatches = new ArrayList<>();
    public void addStopwatches(Stopwatch newStopwatch) {
        stopwatches.add(newStopwatch);
        newStopwatch.setMember(this);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<State> states = new ArrayList<>();
    public void addStates(State state){
        states.add(state);
        state.setMember(this);
    }

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();
    public void addEvents(Event event){
        events.add(event);
        event.setMember(this);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HobbitList> hobbitList = new ArrayList<>();
    public void addHobbit(HobbitList hobbit){
        hobbitList.add(hobbit);
        hobbit.setMember(this);
    }

    public Member(){}
    public Member(List<String> event_participants, List<String> event_activities, List<String> event_emotions, List<String> stopwatch_target) {
        this.event_participants = event_participants;
        this.event_activities = event_activities;
        this.event_emotions = event_emotions;
        this.stopwatch_target = stopwatch_target;
    }

}
