package hobbit.service;

import hobbit.domain.Member;
import hobbit.domain.WeightRecord;
import hobbit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
    public Member findOneWithRecords(Long id) {
        return memberRepository.findOneWithRecords(id);
    }

    public Member findOneAll(Long id){return memberRepository.findOneEveryThing(id);}
    @Transactional
    public void update(Long id, int weight) {
        Member member = memberRepository.findOne(id);

        LocalDateTime currentDateTime = LocalDateTime.now();
        WeightRecord weightRecord = new WeightRecord(currentDateTime,weight,member);
        member.addWeightRecord(weightRecord);
    }
    @Transactional
    public void updateTeamUserName(Long id, String userName) {
        Member member = memberRepository.findOne(id);
        member.setTeamUserName(userName);
    }

    public boolean isMemberPartOfTeam(Long id) {
        try {
            Member member = memberRepository.findMemberWithTeam(id);
            return member.getTeam() != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
