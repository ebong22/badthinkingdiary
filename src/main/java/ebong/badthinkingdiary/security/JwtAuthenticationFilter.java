package ebong.badthinkingdiary.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 *  http 요청이 들어오면 가장 먼저 거치게 되는 filter
 *  Requeset에 포함된 cookie에서 token을 추출하고, token의 payload에 존재하는 email(id??)을 바탕으로 인증에 필요한 토큰을 생성함 (UsernamePasswordToken)
 *  => JWT 토큰과 관련 X / 시큐리티 기본 AuthenticationFilter 역할을 하는 filter인가봄
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter : 한 요청당 단일 실행을 목표로 하는 filter

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 헤더에서 토큰 받아오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);

        if (token != null && jwtTokenProvider.validationToken(token)) {
            // 토큰에서 유저정보 받아 authentication 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token, 'A');
            // SecurityContext에 authentication 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}



// https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/security/JwtAuthorizationFilter.java 여기에 다른 BasicAuthenticationFilter 상속받아서 하는것도 있음
