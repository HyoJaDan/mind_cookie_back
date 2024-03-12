package hobbit.service;

import hobbit.domain.Member;
import hobbit.domain.WeightRecord;
import hobbit.repository.MemberRepository;
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

    public Member findOneAll(Long id){return memberRepository.findOneEveryThing(id);}
    @Transactional
    public void update(Long id, int weight) {
        Member member = memberRepository.findOne(id);

        LocalDateTime currentDateTime = LocalDateTime.now();
        WeightRecord weightRecord = new WeightRecord(currentDateTime,weight,member);
        member.addWeightRecord(weightRecord);
    }

    public void updateTeamUserName(Long id, String userName) {
        Member member = memberRepository.findOne(id);

        member.setTeamUserName(userName);
    }
}
