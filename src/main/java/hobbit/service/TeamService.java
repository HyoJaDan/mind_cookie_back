package hobbit.service;


import hobbit.domain.Team;
import hobbit.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    public List<Team> findTeams() {
        return teamRepository.findAllTeam();
    }
}
