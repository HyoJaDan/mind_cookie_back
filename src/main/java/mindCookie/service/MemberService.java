package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
