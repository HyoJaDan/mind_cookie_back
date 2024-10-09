package mindCookie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mindCookie.domain.DailyHobbitStatus;
import mindCookie.domain.Hobbit;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<LocalDate, Map<Long, Boolean>> getHobbitStatusMap(List<Hobbit> hobbitList, LocalDate startDate, LocalDate endDate) {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT dhs.date, dhs.hobbit.id, (COUNT(dhs) > 0) " +
                        "FROM DailyHobbitStatus dhs " +
                        "WHERE dhs.hobbit IN :hobbitList AND dhs.date BETWEEN :startDate AND :endDate " +
                        "GROUP BY dhs.date, dhs.hobbit.id",
                Object[].class);

        query.setParameter("hobbitList", hobbitList);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Object[]> results = query.getResultList();

        // 날짜별로 hobbit 상태를 저장하는 맵 구성
        Map<LocalDate, Map<Long, Boolean>> statusMap = new HashMap<>();
        for (Object[] result : results) {
            LocalDate date = (LocalDate) result[0];
            Long hobbitId = (Long) result[1];
            Boolean isDone = (Boolean) result[2];

            statusMap
                    .computeIfAbsent(date, k -> new HashMap<>())
                    .put(hobbitId, isDone);
        }

        return statusMap;
    }
    // 특정 hobbit과 관련된 모든 dailyHobbitStatus 삭제
    public void deleteByHobbit(Hobbit hobbit) {
        em.createQuery(
                        "DELETE FROM DailyHobbitStatus d WHERE d.hobbit = :hobbit")
                .setParameter("hobbit", hobbit)
                .executeUpdate();
    }
    public void delete(DailyHobbitStatus dailyHobbitStatus) {
        em.remove(dailyHobbitStatus);
    }

    public void save(DailyHobbitStatus dailyHobbitStatus) {
        em.persist(dailyHobbitStatus);
    }
    // 특정 hobbit과 관련된 모든 dailyHobbitStatus 삭제
}
