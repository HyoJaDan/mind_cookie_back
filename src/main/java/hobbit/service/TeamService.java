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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final PersonalChallengeService personalChallengeService;
    public Team findOne(Long id) {
        return teamRepository.findOne(id);
    }
    public List<Team> findTeams() {
        return teamRepository.findAllTeam();
    }
    public List<Object> getTodayPersonalChallengeStatusByTeamId(Long teamId) {
        List<Long> memberIds = teamRepository.findMemberIdsByTeamId(teamId);
        List<Object> collect = memberIds.stream()
                .map(memberId -> personalChallengeService.getTodayPersonalChallengeStatusByMemberId(memberId))
                .collect(Collectors.toList());
        return collect;
    }


    @Transactional
    public void addMember(Member findMember, Team findTeam) {
        findTeam.addTeamMember(findMember);
    }
}
