package hobbit.repository;

import hobbit.controller.MealRecord.MealRecordDTO;
import hobbit.domain.MealRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
