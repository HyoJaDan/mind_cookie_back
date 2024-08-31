package mindCookie.dto;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;


    // role 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(member.getRole());
            }
        });

        return collection;
    }

    // password 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // username 반환
    @Override
    public String getUsername() {
        return member.getUsername();
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 임의로 만료되지 않았다고 설정
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // 임의로 잠금되지 않았다고 설정
    }

    // 비밀번호 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 임의로 만료되지 않았다고 설정
    }

    // 계정 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // 임의로 활성화 되었다고 설정
    }
}
