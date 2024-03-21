package hobbit.repository;

import hobbit.domain.MealRecord;
import hobbit.domain.Picture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MealRecordRepository {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public Long save(MealRecord mealRecord) {
        if (mealRecord.getId() == null) {
            em.persist(mealRecord); // 엔티티를 저장 (insert)
            em.flush(); // 즉시 데이터베이스에 반영
            return mealRecord.getId(); // 생성된 ID 반환
        } else {
            MealRecord updatedMealRecord = em.merge(mealRecord); // 엔티티를 갱신 (update)
            return updatedMealRecord.getId(); // 갱신된 엔티티의 ID 반환
        }
    }
    // MealRecord 엔티티를 찾는 메소드
    public MealRecord findMealRecordById(Long id) {
        return em.createQuery(
                "SELECT mr FROM MealRecord mr " +
                        "WHERE mr.id = :id", MealRecord.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    // MealRecord 엔티티의 내용(content)를 업데이트하는 메소드
    public void updateMealRecordContent(Long id, String newContent) {
        em.createQuery("UPDATE MealRecord mr SET mr.content = :content WHERE mr.id = :id")
                .setParameter("content", newContent)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void savePicture(Picture picture) {
        em.persist(picture);
    }

    public void saveMealRecord(MealRecord mealRecord) {
        em.persist(mealRecord);
    }

    public Optional<List<MealRecord>> getTodayMealRecordByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        try {
            return Optional.of(em.createQuery(
                            "select mr from MealRecord mr " +
                                    "join fetch mr.picture " +
                                    "join fetch mr.personalChallenge pc " +
                                    "where pc.member.id = :Member_ID " +
                                    "and pc.date = :today", MealRecord.class)
                    .setParameter("Member_ID", memberId)
                    .setParameter("today", today)
                    .getResultList());
        } catch (NoResultException e){
            return Optional.empty();
        }

    }
}
