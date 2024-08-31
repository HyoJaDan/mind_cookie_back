package mindCookie.global.config;

import lombok.RequiredArgsConstructor;
import mindCookie.global.jwt.JWTFilter;
import mindCookie.global.jwt.JWTUtil;
import mindCookie.global.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil; //JWTUtil 주입
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    // BCryptPaasswordEncoder 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        http
                .csrf((auth) -> auth.disable());

        // Form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll() // 모든 사용자 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // admin 권한 사용자만 허용
                        .anyRequest().authenticated()); //permitAll 모든 사용자 허용
                                                    //authenticated 로그인한 사용자만 허용

        // LoginFilter 앞에 JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        // LoginFilter 등록
        http
                .addFilterAt(new LoginFilter( authenticationManager(authenticationConfiguration),jwtUtil ), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정(중요) : JWT를 통한 인증/인가를 위해 세션을 sateless 로 설정!
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}