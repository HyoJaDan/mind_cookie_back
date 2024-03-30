package hobbit.service;


import hobbit.controller.Team.TeamChallengeDTO;
import hobbit.controller.Team.TeamController;
import hobbit.domain.*;
import hobbit.repository.MealRecordRepository;
import hobbit.repository.MemberRepository;
import hobbit.repository.PersonalChallengeRepository;
import hobbit.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final PersonalChallengeRepository personalChallengeRepository;
    private final MealRecordRepository mealRecordRepository;
    public Team findOne(Long id) {
        return teamRepository.findOne(id);
    }
    public List<Team> findTeams() {
        return teamRepository.findAllTeam();
    }
    public TeamChallengeDTO getTodayPersonalChallengeStatusByTeamId(Long teamId) {
        Team team = teamRepository.findOne(teamId);
        List<Member> members = team.getMembers();
        List<TeamChallengeDTO.MemberDTO> result = new ArrayList<>();

        int totalEtcGoals = 0;
        int completedEtcGoals = 0;
        int completedExercises = 0;

        for (Member member : members) {
            Optional<PersonalChallenge> challengeOpt = personalChallengeRepository.findTodayPersonalChallengeByMemberId(member.getId());
            List<MealRecord> todayMealRecords = mealRecordRepository.getTodayMealRecordByMemberId(member.getId()).orElse(new ArrayList<>());
            List<TeamChallengeDTO.MealRecordDTO> mealRecordDTOS = todayMealRecords.stream()
                    .map(TeamChallengeDTO.MealRecordDTO::new)
                    .collect(Collectors.toList());

            if (challengeOpt.isPresent()) {
                PersonalChallenge challenge = challengeOpt.get();
                totalEtcGoals += challenge.getEtcGoals().size();
                completedEtcGoals += (int) challenge.getEtcGoals().stream().filter(EtcGoal::isDone).count();
                completedExercises += challenge.getExercise().isDone() ? 1 : 0; // Assuming only one exercise per day
            }
            TeamChallengeDTO.MemberDTO memberDTO=new TeamChallengeDTO.MemberDTO();
            memberDTO.setMealRecords(mealRecordDTOS);
            memberDTO.setTeamUserName(member.getTeamUserName());
            memberDTO.setMemberId(member.getId());
            result.add(memberDTO);
        }

        TeamChallengeDTO dto = new TeamChallengeDTO();
        dto.setTeamName(team.getTeamName());
        dto.setStartDate(team.getStartDate());
        dto.setEndDate(team.getEndDate());
        dto.setTotalEtcGoals(totalEtcGoals);
        dto.setCompletedEtcGoals(completedEtcGoals);
        dto.setCompletedExercises(completedExercises);
        dto.setMemberDTOS(result);
        return dto;
    }



    @Transactional
    public void addMember(Member findMember, Team findTeam) {
        findTeam.addTeamMember(findMember);
    }
    public Team getTeamByMemberId(Long memberId) {
        return teamRepository.findTeamByMemberId(memberId);
    }
    @Transactional
    public void createTeam(Member findMember, String teamName, LocalDate startDate, ChallngeType challngeType) {
        Team team = new Team(teamName,findMember,5,startDate,challngeType);
        teamRepository.saveTeam(team);
    }
}
