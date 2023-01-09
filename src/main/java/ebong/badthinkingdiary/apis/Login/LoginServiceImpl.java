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


    @Override
    public RefreshToken findRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                        .orElseThrow(()-> new NoSuchElementException(("not exist refreshToken")));
    }

    @Override
    public TokenDTO createToken4Login(Authentication authenticate) {

        TokenDTO tokenDto = createToken(authenticate);
        log.debug("authentication = {}", authenticate); //@TODONOW 나중에 삭제

        saveRefreshToken(tokenDto.getRefreshToken());

        return tokenDto;
    }

    @Override
    public void logout(Long memberId){
        refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("not login member"));

        refreshTokenRepository.deleteByMemberId(memberId);
    }

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

    @Override
    public void saveRefreshToken(String refreshToken){
        Instant expireDate = jwtTokenProvider.getExpiration(refreshToken, 'R').toInstant();
        Member loginMember = memberRepository.findByUserId(jwtTokenProvider.getUserPk(refreshToken, 'R'))
                .orElseThrow(()-> new NoSuchElementException("not exist member"));

        if (loginMember.getRefreshToken() != null) {
            loginMember.getRefreshToken().updateToken(refreshToken, expireDate);
        }
        else {
            RefreshToken saveToken = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .member(loginMember)
                    .expireDate(expireDate)
                    .build();
            refreshTokenRepository.save(saveToken);
        }
    }

    public TokenDTO createToken( Authentication authenticate ){
        String accessToken = jwtTokenProvider.createToken(authenticate, 'A');
        String refreshToken = jwtTokenProvider.createToken(authenticate, 'R');
        return new TokenDTO(accessToken, refreshToken);
    }

}
