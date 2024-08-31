package mindCookie.service;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.CustomUserDetails;
import mindCookie.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        Member member = memberOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        return new CustomUserDetails(member);

    }
//    @Override
//    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//
//        Optional<Member> memberOptional = memberRepository.findById(Long.valueOf(id));
//        Member member = memberOptional.orElseThrow(() ->
//                new UsernameNotFoundException("User not found with id: " + id));
//
//        return new CustomUserDetails(member);
//
//    }
}