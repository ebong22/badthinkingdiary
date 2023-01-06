package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.domain.RefreshToken;
import ebong.badthinkingdiary.domain.RoleList;
import ebong.badthinkingdiary.dto.TokenDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoginService {

    void saveRefreshToken(String refreshToken);

    RefreshToken findRefreshToken(String refreshToken);

    TokenDTO createToken4Login(Authentication authenticate);

    TokenDTO refresh(RefreshToken refreshToken);
}
