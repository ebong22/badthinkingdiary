package ebong.badthinkingdiary.security;

import ebong.badthinkingdiary.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// https://ppaksang.tistory.com/12 참고중 // role관련 entity부분도 있음
// https://jooky.tistory.com/5 참고중 ( 더 간결함 )
// https://www.toptal.com/spring/spring-security-tutorial
// https://llshl.tistory.com/28 로그인

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 이거 써논데도 있던데 뭔지 찾아보기
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 암호화에 필요한 passwordEncoder bean 등록
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@TODO 시큐리티 : 설정값들 뭔지 찾아보고 바꿔야할 것 바꾸기
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
//        return http
//                .httpBasic().disable()
//                .csrf().disable() //csrf disable??
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//                .authorizeRequests()
//                .antMatchers("/api/v1/**").permitAll()
//                .antMatchers("/test").hasRole("USER")
//                .antMatchers("/api/user/**").hasRole("USER")
//            .and()
//                .addFilterBefore(jwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // form전송 필터 이전에 jwt filter를 두어 jwt 인증으로 바꾸는 부분
//                .build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
        return http
                    //h2 콘솔 사용
                    .csrf().disable().headers().frameOptions().disable()
                .and()

                    //세션 사용 안함
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                    //URL 관리
                    .authorizeRequests()
                    .antMatchers("/member/save"
                                    , "/login").permitAll()
                    .antMatchers("/h2-console/**"
                                    , "/swagger-resources/**"
                                    , "/swagger-ui/**"
                                    , "/v3/api-docs"
                                    , "/webjars/**").permitAll()
                    .anyRequest().permitAll()
//                    .anyRequest().authenticated()
                .and()

                    // JwtAuthenticationFilter를 먼저 적용
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
