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

    public List<Team> findAllTeam() {
        List<Team> result=em.createQuery(
                "select distinct t from Team t" +
                " join fetch t.members m", Team.class)
                .getResultList();

        return result;
    }
}
