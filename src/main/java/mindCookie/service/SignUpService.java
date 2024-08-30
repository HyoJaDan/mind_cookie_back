package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
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

        String encodedPassword = bCryptPasswordEncoder.encode(signUpDTO.getPassword());
        boolean isUserExist = memberRepository.existsByUsername(username);
        if (isUserExist){
            return false;
        }

        List<String> eventParticipants = new ArrayList<>();
        List<String> eventActivities = new ArrayList<>();
        List<String> eventEmotions = new ArrayList<>();
        List<String> stopwatchTarget = new ArrayList<>();

        Member member = new Member(username, encodedPassword, eventParticipants, eventActivities, eventEmotions, stopwatchTarget);

        memberRepository.save(member);
        return true;
    }

}