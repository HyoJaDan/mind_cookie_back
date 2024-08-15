package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Stopwatch;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class StopwatchRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<List<Stopwatch>> findByDate(Long memberId, LocalDate today) {
        List<Stopwatch> result = em.createQuery(
                "select s from Stopwatch s"+
                        " where s.member.id = :memberId"+
                        " and s.date = :date",
                    Stopwatch.class)
                .setParameter("memberId",memberId)
                .setParameter("date", today)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
