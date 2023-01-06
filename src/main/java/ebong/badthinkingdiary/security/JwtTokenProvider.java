package ebong.badthinkingdiary.security;

import ebong.badthinkingdiary.domain.RefreshToken;
import ebong.badthinkingdiary.domain.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * JWT 토큰 생성 및 검증 모듈
 */
@Slf4j
@Component
//@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenProvider( UserDetailsService userDetailsService
                            , RefreshTokenRepository refreshTokenRepository
                            , @Value("${spring.jwt.secret}") String secretKey
                            , @Value("${spring.jwt.secret-refresh}") String secretKey4Refresh
                            , @Value("${spring.jwt.token-expiration-access}") String tokenExpirationAccess
                            , @Value("${spring.jwt.token-expiration-refresh}") String tokenExpirationRefresh ){
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.secretKey4Refresh = Base64.getEncoder().encodeToString(secretKey4Refresh.getBytes());
        this.TOKEN_VALID_MILLISECOND = Long.parseLong(tokenExpirationAccess);
        this.REFRESH_TOKEN_VALID_MILLISECOND = Long.parseLong(tokenExpirationRefresh);
    }
    // 암호화키
    private final String secretKey;
    private final String secretKey4Refresh;
    // 토큰 유지시간
    private final long TOKEN_VALID_MILLISECOND;
    private final long REFRESH_TOKEN_VALID_MILLISECOND;


    /**
     * JWT token 생성
     * @param authenticate
     * @param roles
     * @param tokenType
     * @return
     */
    public String createToken(Authentication authenticate, char tokenType) { //tokenType : A = access, R = refresh
        Claims claims = Jwts.claims().setSubject(authenticate.getName());

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(new Date()) // 토큰 발행일자
                .setExpiration(getExpiration(tokenType)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS512, getEncryptKey(tokenType)) // 암호화 알고리즘, 암호키
                .compact();
    }


    /**
     * tokenType에 따른 암호화키 return
     * @param tokenType
     * @return encryptKey
     */
    private String getEncryptKey(char tokenType) {
        // access 토큰 / refresh 토큰 암호화 키 구분
        String encryptKey = tokenType == 'A' ? secretKey : tokenType == 'R' ? secretKey4Refresh : null;

        if( encryptKey == null ){
            throw new IllegalArgumentException("Bad tokenType");
        }
        return encryptKey;
    }


    /**
     * tokenType에 따른 만료 시간 return
     * @param tokenType
     * @return expire time
     */
    private Date getExpiration(char tokenType){
        Date now = new Date();
        Date expireTime = tokenType == 'A' ? new Date(now.getTime() + TOKEN_VALID_MILLISECOND)
                  : tokenType == 'R' ? new Date(now.getTime() + REFRESH_TOKEN_VALID_MILLISECOND) : null;

        if( expireTime == null ){
            throw new IllegalArgumentException("Bad tokenType");
        }
        return expireTime;
    }


    /**
     * JWT token으로 인증 정보 조회
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token, char tokenType) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token, tokenType));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    
    /**
     * JWT token에서 회원정보 추출
     * @param token
     * @return
     */
    public String getUserPk(String token, char tokenType) {
        return Jwts.parser().setSigningKey(getEncryptKey(tokenType)).parseClaimsJws(token).getBody().getSubject();
    }


    /**
     * JWT token에서 만료일 추출
     * @param token
     * @param tokenType
     * @return expiration
     */
    public Date getExpiration(String token, char tokenType) {
        return Jwts.parser().setSigningKey(getEncryptKey(tokenType)).parseClaimsJws(token).getBody().getExpiration();
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
     * ACCESS token 유효성 + 만료일자 확인
     * @param jwtToken
     * @return
     */
    public boolean validationToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

            log.debug("claims = {}", claims); //@TODONOW 나중에 삭제

//            Jwts.parser().setSigningKey(getEncryptKey(tokenType)).parseClaimsJws(token).getBody().getExpiration();

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @TODOnow 여기하는중 ******************
     * 리프레시 토큰 발급 하고 액세스토큰 만료 시 /refresh로 요청하여 검증 후
     * 유효한 리프레시 토큰이면 액세스토큰과 리프레시 토큰 재발급해줘야 함
     * 
     * 만료된 refresh 토큰 삭제 처리
     * 특정 토킅 즉시 만료처리
     *
     */


    /**
     * refresh token 검증 및 재발급
     * @param refreshToken
     * @return
     * @throws RuntimeException
     */
    public String reissueRefreshToken(String refreshToken) throws RuntimeException{
        // refresh token을 디비의 그것과 비교해보기
        RefreshToken findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchElementException("not exist refreshToken"));

        // 토큰 만료기간 체크
        if ( findRefreshToken.getExpireDate().isBefore(Instant.now()) ){
            throw new IllegalArgumentException("expired refreshToken");
        }
        Authentication authentication = getAuthentication(refreshToken, 'R');

        // 새로운 토큰 생성
        String newRefreshToken = createToken(authentication, 'R');
        findRefreshToken.updateToken(newRefreshToken, Instant.now().plusMillis(REFRESH_TOKEN_VALID_MILLISECOND));
        return newRefreshToken;
    }


    /**
     * 만료일 지난 refreshToken 삭제
     * @param date
     */
    @Transactional
    public void expireRefreshTokenLessThan(Instant date) {
        refreshTokenRepository.deleteAllByExpireDateLessThan(date);
    }

}
