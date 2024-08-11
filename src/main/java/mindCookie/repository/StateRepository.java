package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import mindCookie.domain.State;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class StateRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<State> findByDate(Long memberId, LocalDate today){
        return em.createQuery(
                        "select s from State s"+
                                " where s.member.id = :memberId"+
                                " and s.date = :date",
                        State.class)
                .setParameter("memberId", memberId)
                .setParameter("date", today)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    public void save(State state) {
        em.persist(state);
    }
}
