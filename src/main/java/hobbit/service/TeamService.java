package hobbit.service;


import hobbit.domain.Member;
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
    public Team findOne(Long id) {
        return teamRepository.findOne(id);
    }
    public List<Team> findTeams() {
        return teamRepository.findAllTeam();
    }


    @Transactional
    public void addMember(Member findMember, Team findTeam) {
        findTeam.addTeamMember(findMember);
    }
}
