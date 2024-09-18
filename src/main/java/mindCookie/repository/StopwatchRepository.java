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

    // 모든 날짜의 스탑워치 목록을 멤버 ID 기준으로 가져오는 쿼리
    public List<Stopwatch> findAllByMember(Long memberId) {
        return em.createQuery(
                        "select s from Stopwatch s"+
                                " where s.member.id = :memberId",
                        Stopwatch.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

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
    // 특정 멤버와 타겟에 해당하는 모든 스탑워치 데이터를 조회하는 메소드
    public List<Stopwatch> findAllByMemberAndTarget(Long memberId, String target) {
        return em.createQuery(
                        "select s from Stopwatch s"+
                                " where s.member.id = :memberId"+
                                " and s.target = :target"
                        , Stopwatch.class)
                .setParameter("memberId", memberId)
                .setParameter("target", target)
                .getResultList();
    }

    // 스탑워치 엔티티 삭제
    public void delete(Stopwatch stopwatch) {
        em.remove(stopwatch);
    }
}
