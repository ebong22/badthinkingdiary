package ebong.badthinkingdiary.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JWT 토큰 생성 및 검증 모듈
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("spring.jwt.secret")
    private String secretKey;

    // 토큰 유효시간 : 30뷴
    private long tokenValidMillisecond = 1000L * 60 * 30;

    @PostConstruct
    public void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    /**
     * JWT token 생성
     * @param userId
     * @param roles
     * @return JWT token
     */
//    public String createToken(String userId, List<String> roles) {
//        Claims claims = Jwts.claims().setSubject(userId);
//        claims.put("roles", roles);
//
//        Date now = new Date();
//
//        return Jwts.builder()
//                .setClaims(claims) // 데이터
//                .setIssuedAt(now) // 토큰 발행일자
//                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 유효시간
//                .signWith(SignatureAlgorithm.ES256, secretKey) // 암호화 알고리즘, 암호키
//                .compact();
//    }
    public String createToken(Authentication authenticate, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(authenticate.getName());
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 알고리즘, 암호키
                .compact();
    }

    
    /**
     * JWT token으로 인증 정보 조회
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    
    /**
     * JWT token에서 회원정보 추출
     * @param token
     * @return
     */
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    /**
     * RequestHeader에서  token 파싱<br>
     * X-AUTH_TOKEN : JWT token
     * @param request
     * @return JWT token
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    
    /**
     * JWT token 유효성 + 만료일자 확인
     * @param jwtToken
     * @return
     */
    public boolean validationToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }



}
