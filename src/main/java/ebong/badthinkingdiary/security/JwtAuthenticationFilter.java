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
            Authentication authenticate = jwtTokenProvider.getAuthentication(token, 'A');
            // SecurityContext에 authentication 저장
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }

        chain.doFilter(request, response);
    }

}