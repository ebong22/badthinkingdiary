package ebong.badthinkingdiary.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 이거 써논데도 있던데 뭔지 찾아보기
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    // 암호화에 필요한 passwordEncoder bean 등록
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@TODO 시큐리티 : 설정값들 뭔지 찾아보고 바꿔야할 것 바꾸기
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
        return http
                    //h2 콘솔 사용
                    .csrf().disable().headers().frameOptions().disable() // @TODOnow 추후삭제?
                .and()
                    //세션 사용 안함
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()// @TODOnow accessDeniedHandler 설정 확인
                    .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                    //URL 관리
                    .authorizeRequests()
                    .antMatchers("/member/save"
                                    , "/login"
                                    , "/logout/*"
                                    , "/refresh"
                                    ).permitAll()
                    .antMatchers("/h2-console/**"
                                    , "/swagger-resources/**"
                                    , "/swagger-ui/**"
                                    , "/v3/api-docs"
                                    , "/webjars/**").permitAll()
//                    .anyRequest().permitAll()
                    .antMatchers(   "/role/save/member-role"
                                    , "/role/save/new-role"
                                    , "/member/find/*"
//                                    , "/member/delete/*"
//                                    , "/member/update/*"
                                    , "/diary/private/delete/*"
                                    , "/diary/public/delete/*"
                                ).hasAuthority("ADMIN") // 인가처리할 때 이렇게 하면 됨
                    .anyRequest().authenticated()
                .and()
                    // JwtAuthenticationFilter 적용
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }

}
