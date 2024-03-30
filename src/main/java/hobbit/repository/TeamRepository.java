package hobbit.repository;

import hobbit.domain.Team;
import hobbit.service.TeamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class TeamRepository {
    @PersistenceContext
    private EntityManager em;

    public void saveTeam(Team team) {
        em.persist(team);
    }

    public Team findOne(Long id) {
        return em.createQuery(
                        "select t from Team t"+
                                " where t.id = :id",Team.class)
                .setParameter("id",id)
                .getSingleResult();
    }
    public List<Team> findAllTeam() {
        List<Team> result=em.createQuery(
                "select distinct t from Team t" +
                " join fetch t.members m", Team.class)
                .getResultList();

        return result;
    }
    public Team findTeamByMemberId(Long memberId) {
        return em.createQuery("select m.team from Member m where m.id = :memberId", Team.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
    public List<Long> findMemberIdsByTeamId(Long teamId) {
        return em.createQuery(
                        "SELECT m.id FROM Member m WHERE m.team.id = :teamId", Long.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

}
