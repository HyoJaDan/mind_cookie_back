package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.Role;
import mindCookie.dto.SignUpDTO;
import mindCookie.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;  // 암호화에 필요

    @Transactional
    public boolean signUpProcess(SignUpDTO signUpDTO) {
        String username = signUpDTO.getUsername();

        // username으로 기존 회원이 있는지 확인
        boolean isUserExist = memberRepository.existsByUsername(username);
        if (isUserExist) {
            return false; // 이미 존재하는 사용자이므로 회원가입 실패
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpDTO.getPassword());

        List<String> eventParticipants = new ArrayList<>();
        List<String> eventActivities = new ArrayList<>();
        List<String> eventEmotions = new ArrayList<>();
        List<String> stopwatchTarget = new ArrayList<>();

        Member member = new Member(username, encodedPassword, Role.USER,eventParticipants, eventActivities, eventEmotions, stopwatchTarget);

        memberRepository.save(member);
        return true;
    }

}