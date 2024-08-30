package mindCookie.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mindCookie.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public Optional<Member> findById(Long id) {
        return em.createQuery(
                        "select distinct m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }
    // username 기반으로 회원 존재 여부 확인 메서드
    public Boolean existsByUsername(String username) {
        Long count = em.createQuery(
                        "select count(m) from Member m where m.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
    public void save(Member member) {
        if (member.getId() == null) {
            em.persist(member); // 새 엔티티 저장
        } else {
            em.merge(member);   // 기존 엔티티 업데이트
        }
    }
}
