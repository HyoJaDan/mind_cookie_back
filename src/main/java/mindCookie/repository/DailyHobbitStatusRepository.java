package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mindCookie.domain.DailyHobbitStatus;
import mindCookie.domain.Hobbit;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class DailyHobbitStatusRepository {

    @PersistenceContext
    private EntityManager em;

    public boolean existsByHobbitListAndDate(Hobbit hobbitList, LocalDate date) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(dhs) FROM DailyHobbitStatus dhs"+
                        " WHERE dhs.hobbit = :hobbit AND dhs.date = :date",
                Long.class);
        query.setParameter("hobbit", hobbitList);
        query.setParameter("date", date);

        return query.getSingleResult() > 0;
    }
    public DailyHobbitStatus findByHobbitListAndDate(Hobbit hobbitList, LocalDate date) {
        return em.createQuery(
                        "SELECT dhs FROM DailyHobbitStatus dhs"+
                                " WHERE dhs.hobbit = :hobbit AND dhs.date = :date",
                        DailyHobbitStatus.class)
                .setParameter("hobbit", hobbitList)
                .setParameter("date", date)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void delete(DailyHobbitStatus dailyHobbitStatus) {
        em.remove(dailyHobbitStatus);
    }

    public void save(DailyHobbitStatus dailyHobbitStatus) {
        em.persist(dailyHobbitStatus);
    }

}
