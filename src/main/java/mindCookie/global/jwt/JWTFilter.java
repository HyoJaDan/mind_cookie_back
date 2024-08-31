package mindCookie.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.Role;
import mindCookie.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil; // JWTUtil 사용하기 위해 주입

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 헤더에서 jwt 토큰 추출
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response); // 다음 필터로 요청, 응답 전달
            return; // 헤더 검증 실패 시 메서드 종료 (필수)
        }

        // Bearer 제거 후 순수 토큰 추출
        String token = authorization.split(" ")[1]; // 요청 헤더에서 추출한 jwt 토큰을 공백을 기준으로 분리하여 생성된 배열에서 두 번째 인자 추출 <= ["Bearer", "토큰값"]

        // 토큰 소멸 여부 검증
        if (jwtUtil.isExpired(token)) {
            System.out.printf("token expired");
            filterChain.doFilter(request, response); // 다음 필터로 요청, 응답 전달
            return; // 토큰 만료 시 메서드 종료 (필수)
        }

        // 토큰에서 username 과 role 추출
        String username = jwtUtil.getUsername(token);
        String roleString = jwtUtil.getRole(token);

        Role role = Role.valueOf(roleString);

        // 추출한 정보로 userEntity 생성
        // role을 포함한 Member 객체 생성
        Member userEntity = new Member(username, "password", role, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());


        // UserDetails 에 userEntity 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 시큐리티 인증 토큰 객체 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 임시 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터로 요청, 응답 전달
        filterChain.doFilter(request, response);
    }
}