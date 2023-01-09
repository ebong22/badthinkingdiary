package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.domain.RefreshToken;
import ebong.badthinkingdiary.dto.TokenDTO;
import org.springframework.security.core.Authentication;

public interface LoginService {

    /**
     * refreshToken DB 저장
     * @param refreshToken
     */
    void saveRefreshToken(String refreshToken);

    /**
     * refresh token 조회
     * @param refreshToken
     * @return
     */
    RefreshToken findRefreshToken(String refreshToken);

    /**
     * token 생성 및 refresh token 저장 (로그인 시)
     * @param authenticate
     * @return
     */
    TokenDTO createToken4Login(Authentication authenticate);

    /**
     * 로그아웃
     * @param memberId
     */
    void logout(Long memberId);

    /**
     * refreshToken을 통한 토큰 재발급
     * @param refreshToken
     * @return
     */
    TokenDTO refresh(RefreshToken refreshToken);
}
