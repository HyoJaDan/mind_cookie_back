package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Event;
import org.springframework.stereotype.Repository;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    @PersistenceContext
    EntityManager em;

    public Optional<List<Event>> findEventsByMemberAndDate(Long memberId, LocalDate today) {
        List<Event> result = em.createQuery(
                        "select e from Event e " +
                                "where e.member.id = :memberId " +
                                "and e.date = :today",
                        Event.class)
                .setParameter("memberId", memberId)
                .setParameter("today", today)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

}
