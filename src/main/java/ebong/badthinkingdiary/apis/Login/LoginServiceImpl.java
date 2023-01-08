package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRepository;
import ebong.badthinkingdiary.domain.RefreshToken;
import ebong.badthinkingdiary.domain.RefreshTokenRepository;
import ebong.badthinkingdiary.dto.TokenDTO;
import ebong.badthinkingdiary.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.NoSuchElementException;

@Slf4j
@Service("loginService")
@RequiredArgsConstructor
@Transactional //@TODO spring? javax?
public class LoginServiceImpl implements LoginService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * refresh token 조회
     * @param refreshToken
     * @return
     */
    @Override
    public RefreshToken findRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                        .orElseThrow(()-> new NoSuchElementException(("not exist refreshToken")));
    }


    /**
     * token 생성 및 refresh token 저장 (로그인 시)
     * @param authenticate
     * @return
     */
    @Override
    public TokenDTO createToken4Login(Authentication authenticate) {

        TokenDTO tokenDto = createToken(authenticate);
        log.debug("authentication = {}", authenticate); //@TODONOW 나중에 삭제

        saveRefreshToken(tokenDto.getRefreshToken());

        return tokenDto;
    }


    /**
     * reissue token <br>
     * refreseh token 검증 및 토큰 재발급
     * @param refreshToken
     * @return
     */
    @Override
    public TokenDTO refresh(RefreshToken refreshToken){
        Authentication authenticate = jwtTokenProvider.getAuthentication(refreshToken.getRefreshToken(), 'R');
        log.debug("authentication = {}", authenticate); //@TODONOW 나중에 삭제

        TokenDTO tokenDto = createToken(authenticate);
        String newRefreshToken = tokenDto.getRefreshToken();

        // 리프레시 토큰 update
        refreshToken.updateToken(newRefreshToken, jwtTokenProvider.getExpiration(newRefreshToken, 'R').toInstant());

        return tokenDto;
    }



    /**
     * token 생성
     * @param authenticate
     * @return
     */
    public TokenDTO createToken( Authentication authenticate ){
        String accessToken = jwtTokenProvider.createToken(authenticate, 'A');
        String refreshToken = jwtTokenProvider.createToken(authenticate, 'R');
        return new TokenDTO(accessToken, refreshToken);
    }



    /**
     * refreshtoken DB 저장
     * @param refreshToken
     */
    @Override
    public void saveRefreshToken(String refreshToken){
        Instant expireDate = jwtTokenProvider.getExpiration(refreshToken, 'R').toInstant();
        Member loginMember = memberRepository.findByUserId(jwtTokenProvider.getUserPk(refreshToken, 'R'))
                .orElseThrow(()-> new NoSuchElementException("not exist member"));


        RefreshToken saveToken = RefreshToken.builder()
                .refreshToken(refreshToken)
                .member(loginMember)
                .expireDate(expireDate)
                .build();

        refreshTokenRepository.save(saveToken);
    }


    @Override
    public void deleteRefreshTokenByMemberId(Long memberId){
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
